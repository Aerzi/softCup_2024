import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { UserState } from "../../types/UserType";
import { getLocalData } from "../../utils/Storage";

const initialState: UserState = {
  role: getLocalData("role") || "student",
  isLogin: getLocalData("isLogin") || false,
  data: getLocalData("user") || null,
};

// 创建slice
export const UserSlice = createSlice({
  name: "User",
  initialState,
  reducers: {
    // 这里定义修改User的action
    updateUser(state, action: PayloadAction<Partial<UserState>>) {
      if (action.payload) {
        return { ...state, ...action.payload };
      }
    },
  },
});

// Export actions
export const { updateUser } = UserSlice.actions;

// Export reducer
export default UserSlice.reducer;
