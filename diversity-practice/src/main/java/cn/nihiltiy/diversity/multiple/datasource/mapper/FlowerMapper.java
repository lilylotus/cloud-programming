package cn.nihiltiy.diversity.multiple.datasource.mapper;


import cn.nihiltiy.diversity.multiple.datasource.entity.Flower;

public interface FlowerMapper {

  Flower searchById(String id);

  Integer insertByEntity(Flower flower);

}
