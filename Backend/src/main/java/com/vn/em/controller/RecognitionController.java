package com.vn.em.controller;

import com.vn.em.base.RestApiV1;
import com.vn.em.base.VsResponseUtil;
import com.vn.em.constant.UrlConstant;
import com.vn.em.domain.dto.pagination.PaginationFullRequestDto;
import com.vn.em.domain.dto.request.RecognitionCreateDto;
import com.vn.em.domain.dto.request.RecognitionUpdateDto;
import com.vn.em.security.CurrentUser;
import com.vn.em.security.UserPrincipal;
import com.vn.em.service.RecognitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class RecognitionController {

    private final RecognitionService recognitionService;

    @Tag(name = "recognition-controller")
    @Operation(summary = "API get recognition by id")
    @GetMapping(UrlConstant.Recognition.GET_BY_ID)
    public ResponseEntity<?> getRecognitionById(@PathVariable Integer id) {
        return VsResponseUtil.success(recognitionService.getById(id));
    }

    @Tag(name = "recognition-controller")
    @Operation(summary = "API get all recognition")
    @GetMapping(UrlConstant.Recognition.GET_ALL)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> getAllRecognition(@RequestParam(name = "departmentId", required = false) Integer departmentId,
                                               @RequestParam(name = "statusId", required = false) Integer statusId,
                                               @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
        return VsResponseUtil.success(recognitionService.getAll(departmentId, statusId, paginationFullRequestDto));
    }

    @Tag(name = "recognition-controller")
    @Operation(summary = "API get all my recognition create")
    @GetMapping(UrlConstant.Recognition.GET_ALL_BY_USER_CREATE)
    @PreAuthorize("hasAnyRole('ROLE_LEADER', 'ROLE_MANAGER')")
    public ResponseEntity<?> getAllMyRecognitionCreate(@RequestParam(name = "departmentId", required = false) Integer departmentId,
                                                       @RequestParam(name = "statusId", required = false) Integer statusId,
                                                       @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto,
                                                       @Parameter(name = "principal", hidden = true)
                                                       @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(recognitionService.getAllMyCreate(user.getId(), departmentId, statusId, paginationFullRequestDto));
    }

    @Tag(name = "recognition-controller")
    @Operation(summary = "API create recognition")
    @PostMapping(UrlConstant.Recognition.CREATE)
    @PreAuthorize("hasAnyRole('ROLE_LEADER', 'ROLE_MANAGER')")
    public ResponseEntity<?> createRecognition(@Valid @RequestBody RecognitionCreateDto recognitionCreateDto) {
        return VsResponseUtil.success(recognitionService.create(recognitionCreateDto));
    }

    @Tag(name = "recognition-controller")
    @Operation(summary = "API update recognition by id")
    @PatchMapping(UrlConstant.Recognition.UPDATE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> updateRecognitionById(@PathVariable Integer id,
                                                   @Valid @RequestBody RecognitionUpdateDto recognitionUpdateDto) {
        return VsResponseUtil.success(recognitionService.updateById(id, recognitionUpdateDto));
    }

    @Tag(name = "recognition-controller")
    @Operation(summary = "API delete recognition by id")
    @DeleteMapping(UrlConstant.Recognition.DELETE)
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ResponseEntity<?> deleteRecognitionById(@PathVariable Integer id) {
        return VsResponseUtil.success(recognitionService.deleteById(id));
    }


}
