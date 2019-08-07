package com.sean.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.HouseSubscribeStatus;
import com.sean.base.*;
import com.sean.dao.HouseMapper;
import com.sean.dto.HouseDTO;
import com.sean.entity.*;
import com.sean.form.*;
import com.sean.service.*;
import javafx.util.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements IHouseService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private ISubwayService subwayService;

    @Autowired
    private ISubwayStationService subwayStationService;

    @Autowired
    private IHouseDetailService houseDetailService;

    @Autowired
    private IHousePictureService housePictureService;

    @Autowired
    private IHouseTagService houseTagService;

    @Autowired
    private IHouseSubscribeService houseSubscribeService;

    @Value("${cdn_prefix}")
    private String cdnPrefix;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResult<HouseDTO> save(HouseForm houseForm) {
        HouseDetail detail = new HouseDetail();
        ServiceResult<HouseDTO> subwayValidtionResult = wrapperDetailInfo(detail, houseForm);
        if (subwayValidtionResult != null) {
            return subwayValidtionResult;
        }

        House house = modelMapper.map(houseForm, House.class);

        Date now = new Date();
        house.setCreateTime(now);
        house.setLastUpdateTime(now);
        house.setAdminId(LoginUserUtil.getLoginUserId());
        houseMapper.insert(house);

        detail.setHouseId(house.getId());
        houseDetailService.save(detail);

        List<HousePicture> housePictures = generatePictures(houseForm, house.getId());
        housePictureService.saveBatch(housePictures);

        HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);

        houseDTO.setHouseDetail(detail);

        houseDTO.setPictures(housePictures);
        houseDTO.setCover(this.cdnPrefix + houseDTO.getCover());

        List<String> tags = houseForm.getTags();
        if (tags != null && !tags.isEmpty()) {
            List<HouseTag> houseTags = new ArrayList<>();
            for (String tag : tags) {
                houseTags.add(HouseTag.builder().houseId(house.getId()).name(tag).build());
            }
            houseTagService.saveBatch(houseTags);
            houseDTO.setTags(tags);
        }

        return new ServiceResult<HouseDTO>(true, null, houseDTO);
    }

    @Override
    @Transactional
    public ServiceResult update(HouseForm houseForm) {
        House house = this.getById(houseForm.getId());
        if (house == null) {
            return ServiceResult.notFound();
        }

        HouseDetail detail = houseDetailService.lambdaQuery().eq(HouseDetail::getHouseId, house.getId()).one();
        if (detail == null) {
            return ServiceResult.notFound();
        }

        ServiceResult wrapperResult = wrapperDetailInfo(detail, houseForm);
        if (wrapperResult != null) {
            return wrapperResult;
        }

        houseDetailService.updateById(detail);

        List<HousePicture> pictures = generatePictures(houseForm, houseForm.getId());
        housePictureService.updateBatchById(pictures);

        if (houseForm.getCover() == null) {
            houseForm.setCover(house.getCover());
        }

        modelMapper.map(houseForm, house);
        house.setLastUpdateTime(new Date());
        this.updateById(house);

        // TODO 引入es
//        if (house.getStatus() == HouseStatus.PASSES.getValue()) {
//            searchService.index(house.getId());
//        }

        return ServiceResult.success();
    }

    @Override
    public ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch searchBody) {
        List<HouseDTO> houseDTOS = new ArrayList<>();
        Page<House> page = new Page<House>(searchBody.getStart() / searchBody.getLength() + 1,
                searchBody.getLength());

        // 为了排序时用
        if ("createTime".equals(searchBody.getOrderBy())) {
            searchBody.setOrderBy("create_time");
        } else if ("watchTimes".equals(searchBody.getOrderBy())) {
            searchBody.setOrderBy("watch_times");
        }

        QueryWrapper<House> queryWrapper = Wrappers.<House>query()
                .eq("admin_id", LoginUserUtil.getLoginUserId())
                .ne("status", HouseStatus.DELETED.getValue())
                .eq(StringUtils.isNotEmpty(searchBody.getCity()), "city_en_name", searchBody.getCity())
                .eq(searchBody.getStatus() != null, "status", searchBody.getStatus())
                .apply(searchBody.getCreateTimeMin() != null, "date_format(create_time,'%Y-%m-%d') >= {0}", DateUtil.getStringYmdByDate(searchBody.getCreateTimeMin()))
                .apply(searchBody.getCreateTimeMax() != null, "date_format(create_time,'%Y-%m-%d') <= {0}", DateUtil.getStringYmdByDate(searchBody.getCreateTimeMax()))
                .like(StringUtils.isNotEmpty(searchBody.getTitle()), "title", searchBody.getTitle())
                .orderBy(true, "asc".equals(searchBody.getDirection()), searchBody.getOrderBy());


        IPage<House> housesPage = this.page(page, queryWrapper);

        List<House> houses = housesPage.getRecords();

        houses.forEach(house -> {
            HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
            houseDTO.setCover(this.cdnPrefix + house.getCover());
            houseDTOS.add(houseDTO);
        });

        return new ServiceMultiResult<>(housesPage.getTotal(), houseDTOS);
    }

    @Override
    public ServiceResult<HouseDTO> findCompleteOne(Long id) {
        House house = this.getById(id);
        if (house == null) {
            return ServiceResult.notFound();
        }

        HouseDetail detailDTO = houseDetailService.lambdaQuery().eq(HouseDetail::getHouseId, id).one();
        List<HousePicture> pictureDTOS = housePictureService.lambdaQuery().eq(HousePicture::getHouseId, id).list();

        List<HouseTag> tags = houseTagService.lambdaQuery().eq(HouseTag::getHouseId, id).list();

        List<String> tagList = new ArrayList<>();
        for (HouseTag tag : tags) {
            tagList.add(tag.getName());
        }

        HouseDTO result = modelMapper.map(house, HouseDTO.class);
        result.setHouseDetail(detailDTO);
        result.setPictures(pictureDTOS);
        result.setTags(tagList);

        if (LoginUserUtil.getLoginUserId() > 0) { // 已登录用户
            HouseSubscribe subscribe = houseSubscribeService.lambdaQuery()
                    .eq(HouseSubscribe::getHouseId, house.getId())
                    .eq(HouseSubscribe::getUserId, LoginUserUtil.getLoginUserId())
                    .one();
            if (subscribe != null) {
                result.setSubscribeStatus(subscribe.getStatus());
            }
        }
        return ServiceResult.of(result);
    }

    @Override
    public ServiceResult removePhoto(Long id) {
        return null;
    }

    @Override
    public ServiceResult updateCover(Long coverId, Long targetId) {
        return null;
    }

    @Override
    @Transactional
    public ServiceResult addTag(Long houseId, String tag) {
        House house = this.getById(houseId);
        if (house == null) {
            return ServiceResult.notFound();
        }

        HouseTag houseTag = houseTagService.lambdaQuery().eq(HouseTag::getName, tag).eq(HouseTag::getHouseId, houseId).one();
        if (houseTag != null) {
            return new ServiceResult(false, "标签已存在");
        }

        houseTagService.save(new HouseTag(houseId, tag));
        return ServiceResult.success();
    }

    @Override
    @Transactional
    public ServiceResult removeTag(Long houseId, String tag) {
        House house = this.getById(houseId);
        if (house == null) {
            return ServiceResult.notFound();
        }

        HouseTag houseTag = houseTagService.lambdaQuery().eq(HouseTag::getName, tag).eq(HouseTag::getHouseId, houseId).one();
        if (houseTag == null) {
            return new ServiceResult(false, "标签不存在");
        }

        houseTagService.removeById(houseTag.getId());
        return ServiceResult.success();
    }

    @Override
    @Transactional
    public ServiceResult updateStatus(Long id, int status) {
        House house = this.getById(id);
        if (house == null) {
            return ServiceResult.notFound();
        }

        if (house.getStatus() == status) {
            return new ServiceResult(false, "状态没有发生变化");
        }

        if (house.getStatus() == HouseStatus.RENTED.getValue()) {
            return new ServiceResult(false, "已出租的房源不允许修改状态");
        }

        if (house.getStatus() == HouseStatus.DELETED.getValue()) {
            return new ServiceResult(false, "已删除的资源不允许操作");
        }

        boolean updateResult = this.lambdaUpdate().eq(House::getId, id).set(House::getStatus, status).update();

        // TODO 上架更新索引 其他情况都要删除索引
//        if (status == HouseStatus.PASSES.getValue()) {
//            searchService.index(id);
//        } else {
//            searchService.remove(id);
//        }
        return ServiceResult.success();
    }

    @Override
    public ServiceMultiResult<HouseDTO> query(RentSearch rentSearch) {
        if (rentSearch.getKeywords() != null && !rentSearch.getKeywords().isEmpty()) {
            ServiceMultiResult<Long> serviceResult = searchService.query(rentSearch);
            if (serviceResult.getTotal() == 0) {
                return new ServiceMultiResult<>(0, new ArrayList<>());
            }

            return new ServiceMultiResult<HouseDTO>(serviceResult.getTotal(), wrapperHouseResult(serviceResult.getResult()));
        }

        return simpleQuery(rentSearch);
    }

    @Override
    public ServiceMultiResult<HouseDTO> wholeMapQuery(MapSearch mapSearch) {
        return null;
    }

    @Override
    public ServiceMultiResult<HouseDTO> boundMapQuery(MapSearch mapSearch) {
        return null;
    }

    @Override
    public ServiceResult addSubscribeOrder(Long houseId) {
        return null;
    }

    @Override
    public ServiceMultiResult<Pair<HouseDTO, HouseSubscribe>> querySubscribeList(HouseSubscribeStatus status, int start, int size) {
        return null;
    }

    @Override
    public ServiceResult subscribe(Long houseId, Date orderTime, String telephone, String desc) {
        return null;
    }

    @Override
    public ServiceResult cancelSubscribe(Long houseId) {
        return null;
    }

    @Override
    public ServiceMultiResult<Pair<HouseDTO, HouseSubscribe>> findSubscribeList(int start, int size) {
        return null;
    }

    @Override
    public ServiceResult finishSubscribe(Long houseId) {
        return null;
    }


    /**
     * 房源详细信息对象填充
     *
     * @param houseDetail
     * @param houseForm
     * @return
     */
    private ServiceResult<HouseDTO> wrapperDetailInfo(HouseDetail houseDetail, HouseForm houseForm) {
        Subway subway = subwayService.getById(houseForm.getSubwayLineId());

        if (subway == null) {
            return new ServiceResult<>(false, "Not valid subway line!");
        }

        SubwayStation subwayStation = subwayStationService.getById(houseForm.getSubwayStationId());

        if (subwayStation == null || !subway.getId().equals(subwayStation.getSubwayId())) {
            return new ServiceResult<>(false, "Not valid subway station!");
        }

        houseDetail.setSubwayLineId(subway.getId());
        houseDetail.setSubwayLineName(subway.getName());

        houseDetail.setSubwayStationId(subwayStation.getId());
        houseDetail.setSubwayStationName(subwayStation.getName());

        houseDetail.setDescription(houseForm.getDescription());
        houseDetail.setAddress(houseForm.getDetailAddress());
        houseDetail.setLayoutDesc(houseForm.getLayoutDesc());
        houseDetail.setRentWay(houseForm.getRentWay());
        houseDetail.setRoundService(houseForm.getRoundService());
        houseDetail.setTraffic(houseForm.getTraffic());

        return null;
    }


    /**
     * 图片对象列表信息填充
     *
     * @param form
     * @param houseId
     * @return
     */
    private List<HousePicture> generatePictures(HouseForm form, Long houseId) {
        List<HousePicture> pictures = new ArrayList<>();
        if (form.getPhotos() == null || form.getPhotos().isEmpty()) {
            return pictures;
        }

        for (PhotoForm photoForm : form.getPhotos()) {
            HousePicture picture = new HousePicture();
            picture.setHouseId(houseId);
            picture.setCdnPrefix(cdnPrefix);
            picture.setPath(photoForm.getPath());
            picture.setWidth(photoForm.getWidth());
            picture.setHeight(photoForm.getHeight());
            pictures.add(picture);
        }
        return pictures;
    }


    private List<HouseDTO> wrapperHouseResult(List<Long> houseIds) {
        List<HouseDTO> result = new ArrayList<>();

        Map<Long, HouseDTO> idToHouseMap = new HashMap<>();
        List<House> houses = this.lambdaQuery().in(House::getId, houseIds).list();
        houses.forEach(house -> {
            HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
            houseDTO.setCover(this.cdnPrefix + house.getCover());
            idToHouseMap.put(house.getId(), houseDTO);
        });

        wrapperHouseList(houseIds, idToHouseMap);

        // 矫正顺序
        for (Long houseId : houseIds) {
            result.add(idToHouseMap.get(houseId));
        }
        return result;
    }


    /**
     * 渲染详细信息 及 标签
     *
     * @param houseIds
     * @param idToHouseMap
     */
    private void wrapperHouseList(List<Long> houseIds, Map<Long, HouseDTO> idToHouseMap) {
        List<HouseDetail> details = houseDetailService.lambdaQuery().in(HouseDetail::getHouseId, houseIds).list();
        details.forEach(houseDetail -> {
            HouseDTO houseDTO = idToHouseMap.get(houseDetail.getHouseId());
            houseDTO.setHouseDetail(houseDetail);
        });

        List<HouseTag> houseTags = houseTagService.lambdaQuery().in(HouseTag::getHouseId, houseIds).list();
        houseTags.forEach(houseTag -> {
            HouseDTO house = idToHouseMap.get(houseTag.getHouseId());
            house.getTags().add(houseTag.getName());
        });
    }
}
