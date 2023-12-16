package org.tommy.dev.studysupporter.api.controller.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tommy.dev.studysupporter.api.controller.member.request.WebMemberSaveRequest;
import org.tommy.dev.studysupporter.api.service.ApiResponse;
import org.tommy.dev.studysupporter.api.service.member.MemberService;

@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> save(
        @Valid @RequestBody WebMemberSaveRequest request
    ) {

        return ApiResponse.created(memberService.save(request.toService()));
    }

}
