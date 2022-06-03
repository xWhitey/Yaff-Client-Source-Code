package store.yaff.feature.impl.misc;

import org.apache.commons.lang3.StringUtils;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.Message;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.ListSetting;

import java.util.ArrayList;

public class FancyChat extends AbstractFeature {
    public static final ListSetting chatStyle = new ListSetting("chatStyle", "None", "Smallcaps", () -> true, "Aesthetic", "Smallcaps", "Anime", "Magic", "Large", "Rounded");
    private final ArrayList<String> alphabet = stringToArrayList("abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    public FancyChat(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(chatStyle);
    }

    private ArrayList<String> stringToArrayList(String stringToTransform) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < stringToTransform.length(); i++) {
            arrayList.add(String.valueOf(stringToTransform.charAt(i)));
        }
        return arrayList;
    }

    private String convertToAbc(String inputString, String alphabetAbc) {
        ArrayList<String> convertedAlphabet = stringToArrayList(alphabetAbc);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i1 = 0; i1 < inputString.length(); i1++) {
            if (alphabet.contains(String.valueOf(inputString.charAt(i1)))) {
                if (alphabet.contains(String.valueOf(inputString.charAt(i1)))) {
                    stringBuilder.append(convertedAlphabet.get(alphabet.indexOf(String.valueOf(inputString.charAt(i1)))));
                }
            } else {
                stringBuilder.append(inputString.charAt(i1));
            }
        }
        return StringUtils.normalizeSpace(String.valueOf(stringBuilder));
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Message.Send messageEvent) {
            if (chatStyle.getListValue().equalsIgnoreCase("Aesthetic")) {
                messageEvent.setMessage(convertToAbc(messageEvent.getMessage(), "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ１２３４５６７８９０ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ"));
            }
            if (chatStyle.getListValue().equalsIgnoreCase("Smallcaps")) {
                messageEvent.setMessage(convertToAbc(messageEvent.getMessage().toLowerCase(), "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ1234567890"));
            }
            if (chatStyle.getListValue().equalsIgnoreCase("Anime")) {
                messageEvent.setMessage(convertToAbc(messageEvent.getMessage().toLowerCase(), "卂乃匚ᗪ乇千Ꮆ卄丨ﾌҜㄥ爪几ㄖ卩Ɋ尺丂ㄒㄩᐯ山乂ㄚ乙1234567890"));
            }
            if (chatStyle.getListValue().equalsIgnoreCase("Magic")) {
                messageEvent.setMessage(convertToAbc(messageEvent.getMessage().toLowerCase(), "ค๖¢໓ēfງhiวkl๓ຖ໐p๑rŞtนงຟxฯຊ1234567890"));
            }
            if (chatStyle.getListValue().equalsIgnoreCase("Large")) {
                messageEvent.setMessage(convertToAbc(messageEvent.getMessage().toLowerCase(), "ᗩᗷᑕᗪEᖴGᕼIᒍKᒪᗰᑎOᑭᑫᖇᔕTᑌᐯᗯ᙭Yᘔ1234567890"));
            }
            if (chatStyle.getListValue().equalsIgnoreCase("Rounded")) {
                messageEvent.setMessage(convertToAbc(messageEvent.getMessage(), "ⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ①②③④⑤⑥⑦⑧⑨⓪ⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏ"));
            }
        }
    }

}
