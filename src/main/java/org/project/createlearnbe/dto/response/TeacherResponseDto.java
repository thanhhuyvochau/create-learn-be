package org.project.createlearnbe.dto.response;

import org.project.createlearnbe.constant.Gender;

public record TeacherResponseDto(
        long id,
        String firstName,
        String lastName,
        String introduction, // sanitized before returning
        Gender gender
) {}