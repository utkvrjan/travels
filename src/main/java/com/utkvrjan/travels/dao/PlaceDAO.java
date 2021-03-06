package com.utkvrjan.travels.dao;

import com.utkvrjan.travels.entity.Place;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaceDAO extends BaseDAO<Place,String> {


    List<Place>  findByProvinceIdPage(@Param("start") Integer start,@Param("rows") Integer rows,@Param("provinceId") String provinceId);


    Integer findByProvinceIdCounts(String provinceId);




}
