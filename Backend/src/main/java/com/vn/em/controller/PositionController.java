package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.request.PositionCreateDto;
import com.vn.em.domain.dto.request.PositionUpdateDto;
import com.vn.em.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class PositionController {

    private final PositionService positionService;

    @Tag(name = "position-controller")
    @Operation(summary = "API get position by id")
    @GetMapping(UrlConstant.Position.GET_BY_ID)
    public ResponseEntity<?> getPositionById(@PathVariable Integer id) {
        return VsResponseUtil.success(positionService.getById(id));
    }

    @Tag(name = "position-controller")
    @Operation(summary = "API get all position")
    @GetMapping(UrlConstant.Position.GET_ALL)
    public ResponseEntity<?> getAllPosition(@PathVariable Integer departmentId) {
        return VsResponseUtil.success(positionService.getAll(departmentId));
    }

    @Tag(name = "position-controller")
    @Operation(summary = "API create position")
    @PostMapping(UrlConstant.Position.CREATE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> createPosition(@Valid @RequestBody PositionCreateDto positionCreateDto) {
        return VsResponseUtil.success(positionService.create(positionCreateDto));
    }

    @Tag(name = "position-controller")
    @Operation(summary = "API update position")
    @PatchMapping(UrlConstant.Position.UPDATE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> updatePosition(@PathVariable Integer id,
                                            @Valid @RequestBody PositionUpdateDto positionUpdateDto) {
        return VsResponseUtil.success(positionService.update(id, positionUpdateDto));
    }

    @Tag(name = "position-controller")
    @Operation(summary = "API delete position")
    @DeleteMapping(UrlConstant.Position.DELETE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> deletePosition(@PathVariable Integer id) {
        return VsResponseUtil.success(positionService.delete(id));
    }

}
