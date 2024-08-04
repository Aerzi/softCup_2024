export function obfuscatePhone(phone: string) {
  if (!phone) return "";
  const visiblePart = phone.length >= 4 ? phone.slice(-4) : phone;
  return `${phone.startsWith("1") ? "1" : ""}******${visiblePart}`;
}

export function obfuscateName(name: string, visibleCount = 1) {
  if (!name) return "";
  // 确保可见字符数量不为负数或超过姓名长度
  visibleCount = Math.min(Math.max(visibleCount, 0), name.length);

  // 脱敏姓名，只显示前面visibleCount个字符，其余用*替换
  const obfuscatedName =
    name.substring(0, visibleCount) + "*".repeat(name.length - visibleCount);
  return obfuscatedName;
}
