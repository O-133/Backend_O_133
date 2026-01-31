package com.example.demo.disease.dto;

import com.example.demo.disease.domain.Disease;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DiseaseDto {

    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private String name;

        public Disease toEntity() {
            return new Disease(name);
        }
    }

    @Getter
    @Builder
    public static class Response {
        private Integer id;
        private String name;

        public static Response from(Disease disease) {
            return Response.builder()
                    .id(disease.getId())
                    .name(disease.getName())
                    .build();
        }
    }
}
