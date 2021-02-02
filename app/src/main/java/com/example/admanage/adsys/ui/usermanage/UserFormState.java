package com.example.admanage.system_main.ui.usermanage;

import androidx.annotation.Nullable;

public class UserFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer truenameError;
    @Nullable
    private Integer quondampassError;
    @Nullable
    private Integer newpassError;
    @Nullable
    private Integer confirmpassError;

    private boolean isDataValid;

    public UserFormState(@Nullable Integer usernameError, @Nullable Integer truenameError, @Nullable Integer quondampassError, @Nullable Integer newpassError, @Nullable Integer confirmpassError) {
        this.usernameError = usernameError;
        this.truenameError = truenameError;
        this.quondampassError = quondampassError;
        this.newpassError = newpassError;
        this.confirmpassError = confirmpassError;
    }

    public UserFormState(boolean isDataValid) {
        this.usernameError = null;
        this.truenameError = null;
        this.quondampassError = null;
        this.newpassError = null;
        this.confirmpassError =null;

        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getTruenameError() {
        return truenameError;
    }

    @Nullable
    public Integer getQuondampassError() {
        return quondampassError;
    }

    @Nullable
    public Integer getNewpassError() {
        return newpassError;
    }

    @Nullable
    public Integer getConfirmpassError() {
        return confirmpassError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
