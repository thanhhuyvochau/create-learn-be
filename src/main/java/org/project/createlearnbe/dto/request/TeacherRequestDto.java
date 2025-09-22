package org.project.createlearnbe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.project.createlearnbe.constant.Gender;

public record TeacherRequestDto(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Introduction is required")
        String introduction,

        @NotNull(message = "Gender is required")
        Gender gender,

        String profileImageUrl
) {
}