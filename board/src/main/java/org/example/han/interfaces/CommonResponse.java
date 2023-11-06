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

    public static <T> CommonResponse<T> from(DetailResponseCode detailResponseCode) {
        return (CommonResponse<T>) CommonResponse.builder()
                .detailCode(detailResponseCode.detailCode)
                .message(detailResponseCode.message)
                .build();
    }

    @Getter
    @RequiredArgsConstructor
    public enum DetailResponseCode {
        CODE_201("201", "created"),
        CODE_500("500-A", "내부 서버 에러!!");

        private final String detailCode;
        private final String message;
    }
}
