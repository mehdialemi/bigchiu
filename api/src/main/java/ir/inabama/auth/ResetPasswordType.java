package ir.inabama.auth;

public class ResetPasswordType {

    private ResetType resetType;
    private String message;

    public ResetType getResetType() {
        return resetType;
    }

    public void setResetType(ResetType resetType) {
        this.resetType = resetType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResetPasswordType mobile() {
        ResetPasswordType resetPasswordType = new ResetPasswordType();
        resetPasswordType.setResetType(ResetType.MOBILE);
        resetPasswordType.setMessage("کد بازیابی به موبایل ارسال شده است");
        return resetPasswordType;
    }

    public static ResetPasswordType email() {
        ResetPasswordType resetPasswordType = new ResetPasswordType();
        resetPasswordType.setResetType(ResetType.EMAIL);
        resetPasswordType.setMessage("لینک بازیابی به ایمیل ارسال شده است");
        return resetPasswordType;
    }
}