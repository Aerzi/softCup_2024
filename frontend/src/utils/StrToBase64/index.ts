function encodeBase64(str: string) {
  const encoder = new TextEncoder();
  const data = encoder.encode(str);
  let result = "";
  for (let i = 0; i < data.length; i++) {
    result += String.fromCharCode(data[i]);
  }
  return btoa(result);
}

export { encodeBase64 };
