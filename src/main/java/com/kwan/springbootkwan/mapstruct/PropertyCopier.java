package com.kwan.springbootkwan.mapstruct;
import org.mapstruct.MappingTarget;

public interface PropertyCopier<TARGET, T> {
    void copy(@MappingTarget TARGET target, T t);
}