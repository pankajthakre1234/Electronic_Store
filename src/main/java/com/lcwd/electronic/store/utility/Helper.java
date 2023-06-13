package com.lcwd.electronic.store.utility;

import com.lcwd.electronic.store.helper.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type)
    {
        List<U> entity = page.getContent();
        List<V> dtolist = entity.stream().map(objects -> new ModelMapper().map(objects,type)).collect(Collectors.toList());

        PageableResponse<V> response= new PageableResponse<>();
        response.setContent(dtolist);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElements(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }
}
