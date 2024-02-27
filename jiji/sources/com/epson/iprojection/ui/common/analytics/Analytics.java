package com.epson.iprojection.ui.common.analytics;

import android.content.Context;
import android.os.Bundle;
import com.epson.iprojection.common.Lg;
import com.epson.iprojection.common.utils.PrefUtils;
import com.epson.iprojection.ui.common.analytics.customdimension.CustomDimensionForAllEvent;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eAudioTransferDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eConnectAsModeratorDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eConnectErrorDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eCustomDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eDefinitionFileDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eFirstTimeProjectionDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eManualSearchDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eOpenedContentsDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePenUsageSituationDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePermissionChangeDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.ePresentationContentsDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRegisteredDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eRequestTransferScreenDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eSearchRouteDimension;
import com.epson.iprojection.ui.common.analytics.customdimension.enums.eWebScreenDimension;
import com.epson.iprojection.ui.common.analytics.event.CameraDisplayEvent;
import com.epson.iprojection.ui.common.analytics.event.CameraPermissionEvent;
import com.epson.iprojection.ui.common.analytics.event.ConnectCompleteEvent;
import com.epson.iprojection.ui.common.analytics.event.ConnectErrorEvent;
import com.epson.iprojection.ui.common.analytics.event.ConnectEvent;
import com.epson.iprojection.ui.common.analytics.event.ConnectionErrorCountEvent;
import com.epson.iprojection.ui.common.analytics.event.DefinitionFileEvent;
import com.epson.iprojection.ui.common.analytics.event.DiscconectEvent;
import com.epson.iprojection.ui.common.analytics.event.DocumentDisplayEvent;
import com.epson.iprojection.ui.common.analytics.event.FirstTimeProjectionEvent;
import com.epson.iprojection.ui.common.analytics.event.LocationPermissionEvent;
import com.epson.iprojection.ui.common.analytics.event.ManualSearchEvent;
import com.epson.iprojection.ui.common.analytics.event.MirroringEvent;
import com.epson.iprojection.ui.common.analytics.event.ModeratorEndEvent;
import com.epson.iprojection.ui.common.analytics.event.ModeratorStartEvent;
import com.epson.iprojection.ui.common.analytics.event.MultiProjectionEvent;
import com.epson.iprojection.ui.common.analytics.event.OperationTimeEvent;
import com.epson.iprojection.ui.common.analytics.event.PenUsageSituationEvent;
import com.epson.iprojection.ui.common.analytics.event.PhotoDisplayEvent;
import com.epson.iprojection.ui.common.analytics.event.PresentationEvent;
import com.epson.iprojection.ui.common.analytics.event.ProjectorHeaderEvent;
import com.epson.iprojection.ui.common.analytics.event.ReceivedImageDisplayEvent;
import com.epson.iprojection.ui.common.analytics.event.ReceivedTransferImageEvent;
import com.epson.iprojection.ui.common.analytics.event.RegisteredEvent;
import com.epson.iprojection.ui.common.analytics.event.RequestTransferScreenEvent;
import com.epson.iprojection.ui.common.analytics.event.SatisfactionEvent;
import com.epson.iprojection.ui.common.analytics.event.SupportEvent;
import com.epson.iprojection.ui.common.analytics.event.WebDisplayEvent;
import com.epson.iprojection.ui.common.analytics.event.WebScreenEvent;
import com.epson.iprojection.ui.common.analytics.event.enums.eCustomEvent;
import com.epson.iprojection.ui.common.analytics.event.screenview.ScreenNameGetter;
import com.epson.iprojection.ui.common.analytics.event.screenview.ScreenNameUtils;
import com.epson.iprojection.ui.common.analytics.userproperty.AppVersionProperty;
import com.epson.iprojection.ui.common.analytics.userproperty.InstallProperty;
import com.epson.iprojection.ui.common.defines.PrefTagDefine;
import com.epson.iprojection.ui.common.singleton.mirroring.MirroringEntrance;
import com.epson.iprojection.ui.engine_wrapper.Pj;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes.dex */
public class Analytics {
    private static final Analytics _inst = new Analytics();
    private final CameraDisplayEvent _cameraDisplayEvent;
    private final CameraPermissionEvent _cameraPermissionEvent;
    private Context _context;
    private final CustomDimensionForAllEvent _customDimensionForAllEvent;
    private final DefinitionFileEvent _definitionFileEvent;
    private final DiscconectEvent _disconnectEvent;
    private final DocumentDisplayEvent _documentDisplayEvent;
    private FirebaseAnalytics _firebaseAnalytics;
    private final LocationPermissionEvent _locationPermissionEvent;
    private final MirroringEvent _mirroringEvent;
    private final ModeratorEndEvent _moderatorEndEvent;
    private final ModeratorStartEvent _moderatorStartEvent;
    private final MultiProjectionEvent _multiProjectionEvent;
    private final PhotoDisplayEvent _photoDisplayEvent;
    private final ProjectorHeaderEvent _projectorHeaderEvent;
    private final ReceivedImageDisplayEvent _receivedImageDisplayEvent;
    private final SatisfactionEvent _satisfactionEvent;
    private final SupportEvent _supportEvent;
    private final WebDisplayEvent _webDisplayEvent;
    private final AppVersionProperty _appVersionProperty = new AppVersionProperty();
    private final InstallProperty _installProperty = new InstallProperty();
    private final ConnectEvent _connectEvent = new ConnectEvent(null);
    private final ConnectCompleteEvent _connectCompleteEvent = new ConnectCompleteEvent(null);
    private final ConnectionErrorCountEvent _connectionErrorCountEvent = new ConnectionErrorCountEvent(null);
    private final ConnectErrorEvent _connectErrorEvent = new ConnectErrorEvent(null);
    private final OperationTimeEvent _operationTimeEvent = new OperationTimeEvent(null);
    private final ManualSearchEvent _manualSearchEvent = new ManualSearchEvent(null);
    private final RegisteredEvent _registeredEvent = new RegisteredEvent(null);
    private final RequestTransferScreenEvent _requestTransferScreenEvent = new RequestTransferScreenEvent(null);
    private final ReceivedTransferImageEvent _receivedTransferImageEvent = new ReceivedTransferImageEvent(null);
    private final FirstTimeProjectionEvent _firstTimeProjectionEvent = new FirstTimeProjectionEvent(null);
    private final PresentationEvent _presentationEvent = new PresentationEvent(null);
    private final WebScreenEvent _webScreenEvent = new WebScreenEvent(null);
    private final PenUsageSituationEvent _penUsageSituationEvent = new PenUsageSituationEvent(null);

    public boolean isSetuped() {
        return this._firebaseAnalytics != null;
    }

    public void setup(Context context) {
        if (this._firebaseAnalytics == null) {
            this._context = context;
            this._firebaseAnalytics = FirebaseAnalytics.getInstance(context);
            updateAnalyticsCollectionEnabled();
        }
    }

    public void sendScreenEvent(String str) {
        if (isSetuped() && isGoogleAnalyticsCooperate() && ScreenNameGetter.isSendable(str)) {
            ScreenNameUtils.Companion.send(this._firebaseAnalytics, str);
        }
    }

    public void updateUnchangeableInfoDuringConnect() {
        this._customDimensionForAllEvent.updateUnchangeableInfoDuringConnect();
    }

    public void setConnectEvent(eSearchRouteDimension esearchroutedimension, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this._connectEvent.set(esearchroutedimension, bArr, bArr2, bArr3);
    }

    public void setConnectCompleteEvent(eSearchRouteDimension esearchroutedimension) {
        this._connectCompleteEvent.setSearchRouteDimension(esearchroutedimension);
    }

    public void incrementConnectionErrorCount() {
        this._connectionErrorCountEvent.incrementConnectionErrorCount();
    }

    public void setConnectCompleteEvent(eAudioTransferDimension eaudiotransferdimension) {
        this._connectCompleteEvent.setAudioTransferDimension(eaudiotransferdimension);
    }

    public void setConnectCompleteEvent(eConnectAsModeratorDimension econnectasmoderatordimension) {
        this._connectCompleteEvent.setConnectAsModeratorDimension(econnectasmoderatordimension);
    }

    public void setConnectCompleteEvent(eOpenedContentsDimension eopenedcontentsdimension) {
        this._connectCompleteEvent.setOpenedContentsDimension(eopenedcontentsdimension);
    }

    public void setConnectCompleteEvent(int i) {
        this._connectCompleteEvent.setUseBandWidthDimension(i);
    }

    public void setConnectErrorEvent(eConnectErrorDimension econnecterrordimension) {
        this._connectErrorEvent.setConnectErrorDimension(econnecterrordimension);
    }

    public void setOperationDimension(eCustomDimension ecustomdimension) {
        this._operationTimeEvent.setDimensionType(ecustomdimension);
    }

    public void resetLaunchTime() {
        this._operationTimeEvent.resetLaunchTime();
    }

    public void resetDisconnectedTime() {
        this._operationTimeEvent.resetDisconnectedTime();
    }

    public void enableLaunchTimeEvent() {
        this._operationTimeEvent.enableLaunchTimeEvent();
    }

    public void resetConnectingTime() {
        this._operationTimeEvent.resetConnectingTime();
    }

    public void setManualSearchEvent(eManualSearchDimension emanualsearchdimension) {
        this._manualSearchEvent.setManualSearchDimension(emanualsearchdimension);
    }

    public void setRegisteredEvent(eRegisteredDimension eregistereddimension) {
        this._registeredEvent.setRegisteredDimension(eregistereddimension);
    }

    public void setRequestTransferScreenEvent(eRequestTransferScreenDimension erequesttransferscreendimension) {
        this._requestTransferScreenEvent.setRequestTransferScreenDimension(erequesttransferscreendimension);
    }

    public void setFirstTimeProjectionEvent(eFirstTimeProjectionDimension efirsttimeprojectiondimension) {
        this._firstTimeProjectionEvent.setFirstTimeProjectionDimension(efirsttimeprojectiondimension);
    }

    public void setPresentationEvent(ePresentationContentsDimension epresentationcontentsdimension) {
        this._presentationEvent.setPresentationContentsDimension(epresentationcontentsdimension);
    }

    public void setPresentationEvent(boolean z) {
        this._presentationEvent.setPresentationImplicitDimension(z);
    }

    public void setWebScreenEvent(eWebScreenDimension ewebscreendimension) {
        this._webScreenEvent.setWebScreenDimension(ewebscreendimension);
    }

    public void setPenUsageSituationEvent(ePenUsageSituationDimension epenusagesituationdimension) {
        this._penUsageSituationEvent.updatePenUseState(epenusagesituationdimension);
    }

    public void setProtocolAndCodec(String str, String str2) {
        this._mirroringEvent.setProtocolAndCodec(str, str2);
    }

    public void startContents(eCustomEvent ecustomevent) {
        if (ecustomevent == eCustomEvent.MIRRORING_START) {
            this._mirroringEvent.setEventType(eCustomEvent.MIRRORING_START);
            sendEvent(eCustomEvent.MIRRORING_START);
        }
        if (!Pj.getIns().isConnected() || MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
            return;
        }
        int i = AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$event$enums$eCustomEvent[ecustomevent.ordinal()];
        if (i == 1) {
            this._photoDisplayEvent.setEventType(eCustomEvent.PHOTO_DISPLAY_START);
            sendEvent(eCustomEvent.PHOTO_DISPLAY_START);
        } else if (i == 2) {
            this._documentDisplayEvent.setEventType(eCustomEvent.DOCUMENT_DISPLAY_START);
            sendEvent(eCustomEvent.DOCUMENT_DISPLAY_START);
        } else if (i == 3) {
            this._webDisplayEvent.setEventType(eCustomEvent.WEB_DISPLAY_START);
            sendEvent(eCustomEvent.WEB_DISPLAY_START);
        } else if (i == 4) {
            this._cameraDisplayEvent.setEventType(eCustomEvent.CAMERA_DISPLAY_START);
            sendEvent(eCustomEvent.CAMERA_DISPLAY_START);
        } else if (i != 5) {
        } else {
            this._receivedImageDisplayEvent.setEventType(eCustomEvent.RECEIVED_IMAGE_DISPLAY_START);
            sendEvent(eCustomEvent.RECEIVED_IMAGE_DISPLAY_START);
        }
    }

    public void endContents(eCustomEvent ecustomevent) {
        if (ecustomevent == eCustomEvent.MIRRORING_END) {
            this._mirroringEvent.setEventType(eCustomEvent.MIRRORING_END);
            sendEvent(eCustomEvent.MIRRORING_END);
        }
        if (!Pj.getIns().isConnected() || MirroringEntrance.INSTANCE.isMirroringSwitchOn()) {
            return;
        }
        switch (ecustomevent) {
            case PHOTO_DISPLAY_END:
                this._photoDisplayEvent.setEventType(eCustomEvent.PHOTO_DISPLAY_END);
                sendEvent(eCustomEvent.PHOTO_DISPLAY_END);
                return;
            case DOCUMENT_DISPLAY_END:
                this._documentDisplayEvent.setEventType(eCustomEvent.DOCUMENT_DISPLAY_END);
                sendEvent(eCustomEvent.DOCUMENT_DISPLAY_END);
                return;
            case WEB_DISPLAY_END:
                this._webDisplayEvent.setEventType(eCustomEvent.WEB_DISPLAY_END);
                sendEvent(eCustomEvent.WEB_DISPLAY_END);
                return;
            case CAMERA_DISPLAY_END:
                this._cameraDisplayEvent.setEventType(eCustomEvent.CAMERA_DISPLAY_END);
                sendEvent(eCustomEvent.CAMERA_DISPLAY_END);
                return;
            case RECEIVED_IMAGE_DISPLAY_END:
                this._receivedImageDisplayEvent.setEventType(eCustomEvent.RECEIVED_IMAGE_DISPLAY_END);
                sendEvent(eCustomEvent.RECEIVED_IMAGE_DISPLAY_END);
                return;
            default:
                return;
        }
    }

    public void endContentsForce() {
        this._photoDisplayEvent.setEventType(eCustomEvent.PHOTO_DISPLAY_END);
        sendEvent(eCustomEvent.PHOTO_DISPLAY_END);
        this._documentDisplayEvent.setEventType(eCustomEvent.DOCUMENT_DISPLAY_END);
        sendEvent(eCustomEvent.DOCUMENT_DISPLAY_END);
        this._webDisplayEvent.setEventType(eCustomEvent.WEB_DISPLAY_END);
        sendEvent(eCustomEvent.WEB_DISPLAY_END);
        this._cameraDisplayEvent.setEventType(eCustomEvent.CAMERA_DISPLAY_END);
        sendEvent(eCustomEvent.CAMERA_DISPLAY_END);
        this._receivedImageDisplayEvent.setEventType(eCustomEvent.RECEIVED_IMAGE_DISPLAY_END);
        sendEvent(eCustomEvent.RECEIVED_IMAGE_DISPLAY_END);
        this._mirroringEvent.setEventType(eCustomEvent.MIRRORING_END);
        sendEvent(eCustomEvent.MIRRORING_END);
    }

    public void resetContentsCount() {
        this._photoDisplayEvent.resetCount();
        this._documentDisplayEvent.resetCount();
        this._webDisplayEvent.resetCount();
        this._cameraDisplayEvent.resetCount();
        this._receivedImageDisplayEvent.resetCount();
        this._mirroringEvent.resetCount();
    }

    public void setDefinitionFileEvent(eDefinitionFileDimension edefinitionfiledimension) {
        this._definitionFileEvent.setDefinitionFileDimension(edefinitionfiledimension);
    }

    public void setCameraPermissionEvent(ePermissionChangeDimension epermissionchangedimension) {
        this._cameraPermissionEvent.set(epermissionchangedimension);
    }

    public void setLocationPermissionEvent(ePermissionChangeDimension epermissionchangedimension) {
        this._locationPermissionEvent.set(epermissionchangedimension);
    }

    public void setMultiProjectionEventType(eCustomEvent ecustomevent) {
        this._multiProjectionEvent.setEventType(ecustomevent);
    }

    public void setSatisfactionEventType(eCustomEvent ecustomevent) {
        this._satisfactionEvent.setSatisfactionEventType(ecustomevent);
    }

    public void setSatisfactionResult(int i) {
        this._satisfactionEvent.setSatisfactionResult(i);
    }

    public void setSupportEventType(eCustomEvent ecustomevent) {
        this._supportEvent.setSupportEventType(ecustomevent);
    }

    public void sendEvent(eCustomEvent ecustomevent, String str) {
        if (!isSetuped()) {
            Lg.e("セットアップがまだです");
        } else if (isGoogleAnalyticsCooperate()) {
            this._customDimensionForAllEvent.updateChangeableInfoDuringConnect();
            Bundle bundle = new Bundle();
            bundle.putString(eCustomDimension.PROJECTOR_STATUS.getDimensionName(), this._customDimensionForAllEvent.getProjectorStatusParam());
            bundle.putString(eCustomDimension.PROJECTOR_TYPE.getDimensionName(), this._customDimensionForAllEvent.getProjectorTypeParam());
            bundle.putString(eCustomDimension.PROJECTOR_NUM.getDimensionName(), this._customDimensionForAllEvent.getSelectedNumParam());
            bundle.putString(eCustomDimension.PROTOCOL_MODE.getDimensionName(), this._customDimensionForAllEvent.getProtocolModeTypeParam());
            bundle.putString(eCustomDimension.PROJECTOR_HEAD.getDimensionName(), this._customDimensionForAllEvent.getProjectorHeaderCodeParam());
            bundle.putString(eCustomDimension.CONNECTION_USERS_NUM.getDimensionName(), this._customDimensionForAllEvent.getConnectionUserNum());
            switch (AnonymousClass1.$SwitchMap$com$epson$iprojection$ui$common$analytics$event$enums$eCustomEvent[ecustomevent.ordinal()]) {
                case 1:
                case 6:
                    this._photoDisplayEvent.send(this._firebaseAnalytics, bundle);
                case 2:
                case 7:
                    this._documentDisplayEvent.send(this._firebaseAnalytics, bundle);
                case 3:
                case 8:
                    this._webDisplayEvent.send(this._firebaseAnalytics, bundle);
                case 4:
                case 9:
                    this._cameraDisplayEvent.send(this._firebaseAnalytics, bundle);
                case 5:
                case 10:
                    this._receivedImageDisplayEvent.send(this._firebaseAnalytics, bundle);
                    break;
                case 11:
                    this._connectEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 12:
                    this._connectCompleteEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 13:
                    this._connectionErrorCountEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 14:
                    this._connectErrorEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 15:
                    this._operationTimeEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 16:
                    this._manualSearchEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 17:
                    this._registeredEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 18:
                    this._requestTransferScreenEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 19:
                    this._receivedTransferImageEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 20:
                    this._firstTimeProjectionEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 21:
                    this._presentationEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 22:
                    this._webScreenEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 23:
                    this._penUsageSituationEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 24:
                case 25:
                    break;
                case 26:
                    this._disconnectEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 27:
                    this._definitionFileEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 28:
                    this._cameraPermissionEvent.send(this._firebaseAnalytics, bundle, ScreenNameUtils.Companion.createScreenName(str));
                    return;
                case 29:
                    this._locationPermissionEvent.send(this._firebaseAnalytics, bundle, ScreenNameUtils.Companion.createScreenName(str));
                    return;
                case 30:
                    this._projectorHeaderEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 31:
                    this._moderatorStartEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 32:
                    this._moderatorEndEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                    this._multiProjectionEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this._satisfactionEvent.send(this._firebaseAnalytics, bundle);
                    return;
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                    this._supportEvent.send(this._firebaseAnalytics, bundle);
                    return;
                default:
                    Lg.e("エラー");
                    return;
            }
            this._mirroringEvent.send(this._firebaseAnalytics, bundle);
        }
    }

    public void sendEvent(eCustomEvent ecustomevent) {
        sendEvent(ecustomevent, null);
    }

    public void updateAnalyticsCollectionEnabled() {
        if (isSetuped()) {
            int readInt = PrefUtils.readInt(this._context, PrefTagDefine.conPJ_USED_ANALYTICS);
            Lg.d("firebase enable = " + readInt);
            this._firebaseAnalytics.setAnalyticsCollectionEnabled(1 == readInt);
            setupAppVersionProperty();
            setupInstallProperty();
        }
    }

    private void setupAppVersionProperty() {
        if (isSetuped() && this._context != null && isGoogleAnalyticsCooperate()) {
            this._appVersionProperty.set(this._firebaseAnalytics, this._context);
        }
    }

    private void setupInstallProperty() {
        this._installProperty.set(this._firebaseAnalytics, this._context);
    }

    public boolean isGoogleAnalyticsCooperate() {
        return 1 == PrefUtils.readInt(this._context, PrefTagDefine.conPJ_USED_ANALYTICS);
    }

    public static Analytics getIns() {
        return _inst;
    }

    private Analytics() {
        PhotoDisplayEvent photoDisplayEvent = new PhotoDisplayEvent(null);
        this._photoDisplayEvent = photoDisplayEvent;
        DocumentDisplayEvent documentDisplayEvent = new DocumentDisplayEvent(null);
        this._documentDisplayEvent = documentDisplayEvent;
        WebDisplayEvent webDisplayEvent = new WebDisplayEvent(null);
        this._webDisplayEvent = webDisplayEvent;
        CameraDisplayEvent cameraDisplayEvent = new CameraDisplayEvent(null);
        this._cameraDisplayEvent = cameraDisplayEvent;
        ReceivedImageDisplayEvent receivedImageDisplayEvent = new ReceivedImageDisplayEvent(null);
        this._receivedImageDisplayEvent = receivedImageDisplayEvent;
        MirroringEvent mirroringEvent = new MirroringEvent(null);
        this._mirroringEvent = mirroringEvent;
        this._disconnectEvent = new DiscconectEvent(null, photoDisplayEvent, documentDisplayEvent, webDisplayEvent, cameraDisplayEvent, receivedImageDisplayEvent, mirroringEvent);
        this._definitionFileEvent = new DefinitionFileEvent(null);
        this._cameraPermissionEvent = new CameraPermissionEvent(null);
        this._locationPermissionEvent = new LocationPermissionEvent(null);
        this._projectorHeaderEvent = new ProjectorHeaderEvent(null);
        this._moderatorStartEvent = new ModeratorStartEvent(null);
        this._moderatorEndEvent = new ModeratorEndEvent(null);
        this._multiProjectionEvent = new MultiProjectionEvent(null);
        this._satisfactionEvent = new SatisfactionEvent(null);
        this._supportEvent = new SupportEvent(null);
        this._customDimensionForAllEvent = new CustomDimensionForAllEvent();
    }
}
