package com.kwan.springbootkwan.mapstruct;

public interface TwoWayConverter<SRC, DEST> extends ToConverter<SRC, DEST>, FromConverter<SRC, DEST> {
}
