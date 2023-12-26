/**
 * 
 */
package com.service.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class PageResponse<T> {
    private List<T> content = new ArrayList<>();
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalItems;
    private Integer totalPages;
    private Boolean last;
}
