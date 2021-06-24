package cn.nihiltiy.diversity.multiple.datasource.service;

import cn.nihiltiy.diversity.multiple.datasource.config.DataSourceEnum;
import cn.nihiltiy.diversity.multiple.datasource.config.DataSourceSwitcher;
import cn.nihiltiy.diversity.multiple.datasource.entity.Flower;
import cn.nihiltiy.diversity.multiple.datasource.mapper.FlowerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MultipleDatasourceService {

    private static final Logger log = LoggerFactory.getLogger(MultipleDatasourceService.class);
    private FlowerMapper flowerMapper;

    public MultipleDatasourceService(FlowerMapper flowerMapper) {
        this.flowerMapper = flowerMapper;
    }


    @DataSourceSwitcher(value = DataSourceEnum.READ)
    public Flower selectId(String id) {
        return flowerMapper.searchById(id);
    }

    @DataSourceSwitcher(value = DataSourceEnum.WRITE)
    public Flower insert(Flower flower) {
        Integer integer = flowerMapper.insertByEntity(flower);
        log.info("insert result [{}], flower [{}]", integer, flower);
        return flower;
    }

}
