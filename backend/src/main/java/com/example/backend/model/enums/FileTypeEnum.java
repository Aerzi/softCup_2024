package com.example.backend.model.enums;

public enum FileTypeEnum {
    TXT("txt"),
    PDF("pdf"),
    DOC("doc", "docx"),
    IMG("jpg", "jpeg", "png","gif"),
    VIDEO("mp4","avi"),
    CODE("c","cpp","java","py","js","ts"),
    UNKNOWN("unknown");

    private final String[] extensions;

    FileTypeEnum(String... extensions) {
        this.extensions = extensions;
    }

    public static FileTypeEnum getTypeByExtension(String extension) {
        for (FileTypeEnum type : FileTypeEnum.values()) {
            for (String ext : type.extensions) {
                if (ext.equalsIgnoreCase(extension)) {
                    return type;
                }
            }
        }
        return FileTypeEnum.UNKNOWN;
    }
}
