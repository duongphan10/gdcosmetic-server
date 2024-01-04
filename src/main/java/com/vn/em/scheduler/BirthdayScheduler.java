package com.vn.em.scheduler;

import com.corundumstudio.socketio.SocketIOServer;
import com.vn.em.constant.CommonConstant;
import com.vn.em.constant.DataConstant;
import com.vn.em.domain.dto.response.NotificationDto;
import com.vn.em.domain.entity.Employee;
import com.vn.em.domain.entity.User;
import com.vn.em.repository.EmployeeRepository;
import com.vn.em.repository.UserRepository;
import com.vn.em.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BirthdayScheduler {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final SocketIOServer server;

    @Scheduled(cron = "0 0 9 * * ?")
    public void checkBirthdays() {
        List<User> users = userRepository.findAll();
        List<Employee> employees = employeeRepository.getAllByBirthday(LocalDate.now());
        NotificationDto notificationDto;
        String message;
        if (!employees.isEmpty()) {
            for (Employee employee : employees) {
                message = String.format(DataConstant.Notification.BIRTH_YOU.getMessage(),
                        employee.getFullName(), employee.getFullName());
                for (User user : users) {
                    if (!Objects.equals(employee.getUser().getId(), user.getId())) {
                        notificationDto = notificationService.create(DataConstant.Notification.BIRTH_YOU.getType(),
                                message, user);
                    } else {
                        notificationDto = notificationService.create(DataConstant.Notification.BIRTH_MY.getType(),
                                DataConstant.Notification.BIRTH_MY.getMessage(), user);
                    }

                    server.getRoomOperations(user.getId().toString())
                            .sendEvent(CommonConstant.Event.SERVER_SEND_NOTIFICATION, notificationDto);
                }
            }
        }
    }

}
