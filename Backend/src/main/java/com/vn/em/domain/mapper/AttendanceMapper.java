package com.vn.em.domain.mapper;

import com.vn.em.domain.dto.request.AttendanceUpdateDto;
import com.vn.em.domain.dto.response.AttendanceDto;
import com.vn.em.domain.entity.Attendance;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AttendanceMapper {

    @Mappings({
            @Mapping(target = "employeeId", source = "employee.id"),
    })
    AttendanceDto mapAttendanceToAttendanceDto(Attendance attendance);

    List<AttendanceDto> mapAttendancesToAttendanceDtos(List<Attendance> attendances);

    void update(@MappingTarget Attendance attendance, AttendanceUpdateDto attendanceUpdateDto);

}
