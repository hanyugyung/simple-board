package org.example.han.interfaces;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CommonResponse<T> {
    private final String detailCode;
    private final String message;
    private final T data;

    public static <T> CommonResponse<T> from(DetailResponseCode detailResponseCode, T data) {
        return (CommonResponse<T>) CommonResponse.builder()
                .detailCode(detailResponseCode.detailCode)
                .message(detailResponseCode.message)
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> from(T data) {
        return (CommonResponse<T>) CommonResponse.builder()
                .detailCode(DetailResponseCode.CODE_200.detailCode)
                .message(DetailResponseCode.CODE_200.message)
                .data(data)
                .build();
    }

    @Getter
    @RequiredArgsConstructor
    public enum DetailResponseCode {
        CODE_200("200", "ok"),
        CODE_200_CREATED("200_C", "created"),
        CODE_200_UPDATED("200_U", "updated"),
        CODE_200_DELETED("200_D", "deleted"),
        CODE_500("500-A", "내부 서버 에러!!");

        private final String detailCode;
        private final String message;
    }
}
