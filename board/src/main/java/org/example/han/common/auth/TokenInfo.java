package org.example.han.common.auth;

import java.util.List;

public record TokenInfo(Long id, String loginId, String userName, List<String> roles) {
    public TokenInfo {
        // 필드에 조건이 필요하면 여기에 조건문 추가하면 됨
    }
}
