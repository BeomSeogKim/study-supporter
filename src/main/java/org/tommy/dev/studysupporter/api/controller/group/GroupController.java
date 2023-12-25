package org.tommy.dev.studysupporter.api.controller.group;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tommy.dev.studysupporter.api.controller.group.request.WebGroupSaveRequest;
import org.tommy.dev.studysupporter.api.service.ApiResponse;
import org.tommy.dev.studysupporter.api.service.group.GroupService;

@RestController
@RequestMapping(value = "/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> save(
        @RequestBody @Valid WebGroupSaveRequest request,
        @RequestParam(value = "memberId", required = true) long memberId
    ) {

        return ApiResponse.created(groupService.save(request.toService(), memberId));
    }

}
