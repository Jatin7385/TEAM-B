package sample;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;

public class MyDataReceiveListener implements IDataReceiveListener {
    @Override
    public void dataReceived(XBeeMessage xbeeMessage) {
        System.out.format("From %s >> %s | %s%n", xbeeMessage.getDevice().get64BitAddress(),
                HexUtils.prettyHexString(HexUtils.byteArrayToHexString(xbeeMessage.getData())),
                new String(xbeeMessage.getData()));
    }
}
