package com.example.backend.model.entity.pdftranslate;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;

import java.util.ArrayList;
import java.util.List;


public class CustomTextExtractionStrategy extends LocationTextExtractionStrategy {
    private final List<TextRenderInfo> textRenderInfos = new ArrayList<>();

    @Override
    public void eventOccurred(IEventData data, EventType type) {
        if (data instanceof TextRenderInfo) {
            TextRenderInfo renderInfo = (TextRenderInfo) data;
            renderInfo.preserveGraphicsState(); // Preserve the graphics state
            textRenderInfos.add(renderInfo);
        }
    }

    public List<TextRenderInfo> getTextRenderInfos() {
        return textRenderInfos;
    }
}