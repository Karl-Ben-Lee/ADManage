package com.example.admanage.system_main.ui.admanage;

import androidx.annotation.Nullable;

public class ADFormState {
    @Nullable
    private Integer adstanderError;
    @Nullable
    private Integer adnameError;
    @Nullable
    private Integer sourpositError;
    @Nullable
    private Integer usepositError;
    @Nullable
    private Integer streetnameError;
    @Nullable
    private Integer duetimeError;
    @Nullable
    private Integer adsizeError;
    @Nullable
    private Integer sourcontactError;
    @Nullable
    private Integer sourphoneError;
    @Nullable
    private Integer usecontactError;
    @Nullable
    private Integer userphoneError;
    @Nullable
    private Integer approvaldate;
    @Nullable
    private Integer startdateError;
    @Nullable
    private Integer movedateError;
    @Nullable
    private Integer feeError;
    @Nullable
    private Integer adcontentError;
    @Nullable
    private Integer adlocationError;
    @Nullable
    private Integer adlongError;
    @Nullable
    private Integer adlatError;

    // 我们用这个字段来控制登录按钮的状态
    private boolean isDataValid;


    public ADFormState(@Nullable Integer adnameError, @Nullable Integer sourpositError, @Nullable Integer usepositError, @Nullable Integer streetnameError, @Nullable Integer duetimeError) {
        this.adnameError = adnameError;
        this.sourpositError = sourpositError;
        this.usepositError = usepositError;
        this.streetnameError = streetnameError;
        this.duetimeError = duetimeError;
        this.isDataValid = false;
    }

    public ADFormState( @Nullable Integer adnameError, @Nullable Integer sourpositError, @Nullable Integer usepositError, @Nullable Integer adcontentError,
                       @Nullable Integer duetimeError, @Nullable Integer adsizeError, @Nullable Integer sourcontactError,
                       @Nullable Integer sourphoneError, @Nullable Integer usecontactError, @Nullable Integer userphoneError,
                       @Nullable Integer approvaldate, @Nullable Integer startdateError, @Nullable Integer movedateError,
                       @Nullable Integer feeError,@Nullable Integer adlocationError,@Nullable Integer adlongError,@Nullable Integer adlatError,@Nullable Integer adstanderError) {
        this.adnameError = adnameError;
        this.sourpositError = sourpositError;
        this.usepositError = usepositError;
        this.adcontentError = adcontentError;
        this.duetimeError = duetimeError;
        this.adsizeError = adsizeError;
        this.sourcontactError = sourcontactError;
        this.sourphoneError = sourphoneError;
        this.usecontactError = usecontactError;
        this.userphoneError = userphoneError;
        this.approvaldate = approvaldate;
        this.startdateError = startdateError;
        this.movedateError = movedateError;
        this.feeError = feeError;
        this.adlocationError=adlocationError;
        this.adlatError=adlatError;
        this.adlongError=adlongError;
        this.adstanderError=adstanderError;
    }

    ADFormState(boolean isDataValid) {
        /*this.usernameError = null;
        this.passwordError = null;*/
        this.adnameError = null;
        this.adstanderError=null;
        this.sourpositError = null;
        this.usepositError = null;
        this.streetnameError = null;
        this.duetimeError = null;

        this.adcontentError = null;
        this.adsizeError = null;
        this.sourcontactError = null;
        this.sourphoneError = null;
        this.usecontactError = null;
        this.userphoneError = null;
        this.approvaldate = null;
        this.startdateError = null;
        this.movedateError = null;
        this.feeError = null;
        this.adlocationError=null;
        this.adlatError=null;
        this.adlongError=null;

        this.isDataValid = isDataValid;
    }


    @Nullable
    public Integer getAdnameError() {
        return adnameError;
    }

    @Nullable
    public Integer getSourpositError() {
        return sourpositError;
    }

    @Nullable
    public Integer getUsepositError() {
        return usepositError;
    }

    @Nullable
    public Integer getStreetnameError() {
        return streetnameError;
    }

    @Nullable
    public Integer getDuetimeError() {
        return duetimeError;
    }

    @Nullable
    public Integer getAdsizeError() {
        return adsizeError;
    }

    @Nullable
    public Integer getSourcontactError() {
        return sourcontactError;
    }

    @Nullable
    public Integer getSourphoneError() {
        return sourphoneError;
    }

    @Nullable
    public Integer getUsecontactError() {
        return usecontactError;
    }

    @Nullable
    public Integer getUserphoneError() {
        return userphoneError;
    }

    @Nullable
    public Integer getApprovaldate() {
        return approvaldate;
    }

    @Nullable
    public Integer getStartdateError() {
        return startdateError;
    }

    @Nullable
    public Integer getMovedateError() {
        return movedateError;
    }

    @Nullable
    public Integer getFeeError() {
        return feeError;
    }

    @Nullable
    public Integer getAdcontentError() {
        return adcontentError;
    }

    @Nullable
    public Integer getAdlocationError() {
        return adlocationError;
    }

    @Nullable
    public Integer getAdlongError() {
        return adlongError;
    }

    @Nullable
    public Integer getAdlatError() {
        return adlatError;
    }

    @Nullable
    public Integer getAdstanderError() {
        return adstanderError;
    }

    /*public void setAdstanderError(@Nullable Integer adstanderError) {
        this.adstanderError = adstanderError;
    }*/

    public boolean isDataValid() {
        return isDataValid;
    }

}
