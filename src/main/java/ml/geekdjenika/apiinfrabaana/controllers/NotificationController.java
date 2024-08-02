package ml.geekdjenika.apiinfrabaana.controllers;

import lombok.RequiredArgsConstructor;
import ml.geekdjenika.apiinfrabaana.dto.notification.NotificationResponse;
import ml.geekdjenika.apiinfrabaana.models.Notification;
import ml.geekdjenika.apiinfrabaana.services.notification.NotificationService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public NotificationResponse save(@RequestBody Notification notification) {
        return service.save(notification);
    }

    @GetMapping("/user/{userId}")
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<NotificationResponse> findByUserId(@PathVariable long userId) {
        return service.findByUserId(userId);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAuthority('USER')")
    public NotificationResponse findById(@PathVariable long id) {
        return service.findById(id);
    }
}
