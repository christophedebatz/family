package tech.btzstudio.family.auth.service;

import tech.btzstudio.family.model.entity.User;

public record UserSession(String token, User user) {
}
