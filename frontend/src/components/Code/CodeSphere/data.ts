export const list = [
  {
    label: "Bosque (latest)",
    value: "bosque",
  },
  {
    label: "C3 (latest)",
    value: "c3",
  },
  {
    label: "C (Clang 10.0.1)",
    value: "c",
  },
  {
    label: "C++ (Clang 10.0.1)",
    value: "cpp",
  },
  {
    label: "C (Clang 9.0.1)",
    value: "clang9",
  },
  {
    label: "C++ (Clang 9.0.1)",
    value: "clang9cpp",
  },
  {
    label: "C# (Mono 6.12.0.122)",
    value: "csharp",
  },
  {
    label: "C# (.NET Core SDK 3.1.406)",
    value: "csharpcore",
  },
  {
    label: "C# (.NET Core SDK 7.0.400)",
    value: "csharpcore7",
  },
  {
    label: "C++ Test (Clang 10.0.1, Google Test 1.8.1)",
    value: "cpptest",
  },
  {
    label: "C++ Test (GCC 8.4.0, Google Test 1.8.1)",
    value: "gcccpptest",
  },
  {
    label: "C# Test (.NET Core SDK 3.1.406, NUnit 3.12.0)",
    value: "csharptest",
  },
  {
    label: "F# (.NET Core SDK 3.1.406)",
    value: "fsharp",
  },
  {
    label: "Java (OpenJDK 14.0.1)",
    value: "java",
  },
  {
    label:
      "Java Test (OpenJDK 14.0.1, JUnit Platform Console Standalone 1.6.2)",
    value: "javatest",
  },
  {
    label: "MPI (OpenRTE 3.1.3) with C (GCC 8.4.0)",
    value: "mpi-c",
  },
  {
    label: "MPI (OpenRTE 3.1.3) with C++ (GCC 8.4.0)",
    value: "mpi-cpp",
  },
  {
    label: "MPI (OpenRTE 3.1.3) with Python (3.7.7)",
    value: "mpi-python",
  },
  {
    label: "Multi-file program",
    value: "multifile",
  },
  {
    label: "Nim (stable)",
    value: "nim",
  },
  {
    label: "Python 2.7 (PyPy 7.3.12)",
    value: "python2",
  },
  {
    label: "Python 3.10 (PyPy 7.3.12)",
    value: "python3",
  },
  {
    label: "Python 3.9 (PyPy 7.3.12)",
    value: "python39",
  },
  {
    label: "Python for ML (3.11.2)",
    value: "python-ml",
  },
  {
    label: "Python for ML (3.7.7)",
    value: "python-ml37",
  },
  {
    label: "Visual Basic.Net (vbnc 0.0.0.5943)",
    value: "vbnet",
  },
];

export const languageIdMap = {
  bosque: 11,
  c3: 3,
  c: 1,
  cpp: 2,
  clang9: 13,
  clang9cpp: 14,
  csharp: 22,
  csharpcore: 21,
  csharpcore7: 29,
  cpptest: 15,
  gcccpptest: 12,
  csharptest: 23,
  fsharp: 24,
  java: 4,
  javatest: 5,
  mpiC: 6,
  mpiCpp: 7,
  mpiPython: 8,
  multifile: 89,
  nim: 9,
  python2: 26,
  python3: 28,
  python39: 27,
  pythonMl: 25,
  pythonMl37: 10,
  vbnet: 20,
} as any;

export const options: any = {
  selectOnLineNumbers: true,
  roundedSelection: false,
  readOnly: false, // 是否只读  取值 true | false
  cursorStyle: "line",
  automaticLayout: true, // 自动布局
};
