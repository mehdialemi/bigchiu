package ir.inabama.user;

import ir.inabama.common.ApiResponse;
import ir.inabama.exceptions.AppException;
import ir.inabama.auth.security.CurrentUser;
import ir.inabama.auth.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> profile(@CurrentUser UserPrincipal currentUser, @RequestBody UserProfile userProfile) {
        if (!currentUser.getUsername().equals(userProfile.getUsername())) {
            return new ResponseEntity<>(
                    new ApiResponse(false, "درخواست برای نام کاربری دیگری نامعتبر است"),
                    HttpStatus.BAD_REQUEST);
        }

        return userResult(userService.updateProfile(userProfile));
    }

    @RequestMapping("activate/check/{code}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> checkMobileActivationCode(@CurrentUser UserPrincipal currentUser,
                                                       @NotNull @PathVariable("code") Integer code) {
        ApiResponse result = userService.checkMobileActivationCode(currentUser.getUsername(), code);
        if (result.getSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("get")
    @PreAuthorize("hasAnyRole('USER','ADMIN','OPERATOR')")
    public ResponseEntity<?> getUser(@CurrentUser UserPrincipal userPrincipal) {
        User user;

        try {
            user = userService.get(userPrincipal.getUsername());
        } catch (AppException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> userResult(Pair<ApiResponse, User> result) {
        if (result.getFirst().getSuccess())
            return new ResponseEntity<>(result.getSecond(), HttpStatus.OK);

        return new ResponseEntity<>(result.getFirst(), HttpStatus.BAD_REQUEST);
    }
}
