// 存储数据到localStorage
export const setLocalData = (key: string, value: any) => {
  localStorage.setItem(key, JSON.stringify(value));
};

// 从localStorage获取数据
export const getLocalData = (key: string) => {
  const item = localStorage.getItem(key);
  return item ? JSON.parse(item) : null;
};
