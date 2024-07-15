import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { UserState } from "../../types/UserType";

const initialState: UserState = {
  role: "student",
};

// 创建slice
export const UserSlice = createSlice({
  name: "User",
  initialState,
  reducers: {
    // 这里定义修改User的action
    updateUser(state, action: PayloadAction<Partial<boolean>>) {
      if (action.payload) {
        state.role = "student";
      } else {
        state.role = "teacher";
      }
    },
  },
});

// Export actions
export const { updateUser } = UserSlice.actions;

// Export reducer
export default UserSlice.reducer;
