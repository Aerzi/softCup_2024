import { configureStore } from "@reduxjs/toolkit";
import userReducer from "../slices/userSlice";

export type RootState = {
  user: ReturnType<typeof userReducer>;

  // 可以继续添加其他模块的状态类型
};

export const store = configureStore({
  reducer: {
    user: userReducer,
  },
});
