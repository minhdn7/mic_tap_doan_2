package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration;

import android.graphics.Typeface;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 4/7/2017.
 */

public class Application extends android.app.Application {

    @Getter private static Application app;
    @Getter @Setter private ApplicationSharedPreferences appPrefs;
    @Getter private Typeface typeface;
    @Getter private int countFunction; // Số lượng chức năng bên menu trái
    @Getter private int timeSync; // Thời gian đồng bộ dữ liệu
    @Getter private String baseAPIUrl;
    @Getter private String baseAPISigningUrl;
    @Getter private String cert;
    @Getter private String keyStorePassword;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        app = this;
        appPrefs = new ApplicationSharedPreferences(this);
        typeface = Typeface.createFromAsset(getAssets(), "arial.ttf");
        countFunction = 15;
        timeSync = 180000;
//        baseAPIUrl = "https://qlvb.caobang.gov.vn";
        baseAPIUrl = "http://14.225.6.6";
//        baseAPIUrl = "https://eoffice.vnpt.vn";
        //baseAPIUrl = "https://edoc.vnptsoftware.vn";
        //baseAPIUrl = "https://edoc.vnptsoftware.vn";
//        baseAPIUrl = "https://vnpt-eoffice.vnptsoftware.vn";
//        baseAPIUrl = "https://vnpt-eoffice.vnptsoftware.vn";
//        baseAPISigningUrl = "http://123.30.60.210:80";
        baseAPISigningUrl = "http://14.225.6.6";
//        baseAPISigningUrl = "https://eoffice.vnpt.vn";
//        cert = "-----BEGIN CERTIFICATE-----\n" +
//                "MIIFWDCCBECgAwIBAgIRAJ430LdVFu046rQpycK/qZgwDQYJKoZIhvcNAQELBQAw\n" +
//                "gZAxCzAJBgNVBAYTAkdCMRswGQYDVQQIExJHcmVhdGVyIE1hbmNoZXN0ZXIxEDAO\n" +
//                "BgNVBAcTB1NhbGZvcmQxGjAYBgNVBAoTEUNPTU9ETyBDQSBMaW1pdGVkMTYwNAYD\n" +
//                "VQQDEy1DT01PRE8gUlNBIERvbWFpbiBWYWxpZGF0aW9uIFNlY3VyZSBTZXJ2ZXIg\n" +
//                "Q0EwHhcNMTcwOTI1MDAwMDAwWhcNMTgwOTI1MjM1OTU5WjBdMSEwHwYDVQQLExhE\n" +
//                "b21haW4gQ29udHJvbCBWYWxpZGF0ZWQxHTAbBgNVBAsTFFBvc2l0aXZlU1NMIFdp\n" +
//                "bGRjYXJkMRkwFwYDVQQDDBAqLmNhb2JhbmcuZ292LnZuMIIBIjANBgkqhkiG9w0B\n" +
//                "AQEFAAOCAQ8AMIIBCgKCAQEAs9NMp3jQSfvk++/MjsHZVAUZGWDRK0gDWZEKw3G5\n" +
//                "+bCargR9E04RrcBZruPgt8ZilPTIgcbqfQ6f1TjeT1liDbJconW/5sY49pFZUmkz\n" +
//                "XLZyzcqDzS4Z4IqejPEoQU9PbdUT7EGRRVDWWTzq1n+LYIbc8YWqQOgKkDpW35Y6\n" +
//                "9BxX62CdzTCpAqvyVbOaNHfRm2YvFp+7JoIqRVeWcb4cHvEbvpgyxL44TppzI7j7\n" +
//                "IvKRBR6UroTfyy3P+P+v89oAe3MzClFEJAluNsk81xVeZFyT5C1CUi9jhRu0APrS\n" +
//                "0UFoHqJ/wI5uh8x0xhbjYLLA1zUBhHaYVHfLPwucIOVCnwIDAQABo4IB3TCCAdkw\n" +
//                "HwYDVR0jBBgwFoAUkK9qOpRaC9iQ6hJWc99DtDoo2ucwHQYDVR0OBBYEFH7bgfru\n" +
//                "Od2//Jg7LDgT1i+RXKlcMA4GA1UdDwEB/wQEAwIFoDAMBgNVHRMBAf8EAjAAMB0G\n" +
//                "A1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjBPBgNVHSAESDBGMDoGCysGAQQB\n" +
//                "sjEBAgIHMCswKQYIKwYBBQUHAgEWHWh0dHBzOi8vc2VjdXJlLmNvbW9kby5jb20v\n" +
//                "Q1BTMAgGBmeBDAECATBUBgNVHR8ETTBLMEmgR6BFhkNodHRwOi8vY3JsLmNvbW9k\n" +
//                "b2NhLmNvbS9DT01PRE9SU0FEb21haW5WYWxpZGF0aW9uU2VjdXJlU2VydmVyQ0Eu\n" +
//                "Y3JsMIGFBggrBgEFBQcBAQR5MHcwTwYIKwYBBQUHMAKGQ2h0dHA6Ly9jcnQuY29t\n" +
//                "b2RvY2EuY29tL0NPTU9ET1JTQURvbWFpblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJD\n" +
//                "QS5jcnQwJAYIKwYBBQUHMAGGGGh0dHA6Ly9vY3NwLmNvbW9kb2NhLmNvbTArBgNV\n" +
//                "HREEJDAighAqLmNhb2JhbmcuZ292LnZugg5jYW9iYW5nLmdvdi52bjANBgkqhkiG\n" +
//                "9w0BAQsFAAOCAQEAFcUpPXNui2zaqQO66tQHBcBtEvm2W0J7QvBrgOuJrhaWLwxG\n" +
//                "8VhbS0S9WCfjo0I0Ubi3Xq+l4gGlayvCXCTYC7BQesxNBcEHKfU5A/f/2sOmgG6d\n" +
//                "aTzo/LKU0wYjBbkDZ85Tk0sy2MIR/GSeIVMa3rN70kGS1P3duOehvpbvLMYof6xV\n" +
//                "LKvz+TLDc5Pzk3eSn0PZrCYAADqc4HHD8I38d/x9grXm/pQuZ6dn2yZRV/tQJL6W\n" +
//                "bvyiqc0oxoFw78LgeFVkwjGCWPvHPcxtpIls5ZaTmI1WjcZeQdHolKXmlIWDCQjz\n" +
//                "zqvF6wod9nXM6bygaTp9bgwWiKcD65KNpeln0g==\n" +
//                "-----END CERTIFICATE-----";
        cert = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDxTCCAq2gAwIBAgIQAqxcJmoLQJuPC3nyrkYldzANBgkqhkiG9w0BAQUFADBs\n" +
                "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\n" +
                "d3cuZGlnaWNlcnQuY29tMSswKQYDVQQDEyJEaWdpQ2VydCBIaWdoIEFzc3VyYW5j\n" +
                "ZSBFViBSb290IENBMB4XDTA2MTExMDAwMDAwMFoXDTMxMTExMDAwMDAwMFowbDEL\n" +
                "MAkGA1UEBhMCVVMxFTATBgNVBAoTDERpZ2lDZXJ0IEluYzEZMBcGA1UECxMQd3d3\n" +
                "LmRpZ2ljZXJ0LmNvbTErMCkGA1UEAxMiRGlnaUNlcnQgSGlnaCBBc3N1cmFuY2Ug\n" +
                "RVYgUm9vdCBDQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMbM5XPm\n" +
                "+9S75S0tMqbf5YE/yc0lSbZxKsPVlDRnogocsF9ppkCxxLeyj9CYpKlBWTrT3JTW\n" +
                "PNt0OKRKzE0lgvdKpVMSOO7zSW1xkX5jtqumX8OkhPhPYlG++MXs2ziS4wblCJEM\n" +
                "xChBVfvLWokVfnHoNb9Ncgk9vjo4UFt3MRuNs8ckRZqnrG0AFFoEt7oT61EKmEFB\n" +
                "Ik5lYYeBQVCmeVyJ3hlKV9Uu5l0cUyx+mM0aBhakaHPQNAQTXKFx01p8VdteZOE3\n" +
                "hzBWBOURtCmAEvF5OYiiAhF8J2a3iLd48soKqDirCmTCv2ZdlYTBoSUeh10aUAsg\n" +
                "EsxBu24LUTi4S8sCAwEAAaNjMGEwDgYDVR0PAQH/BAQDAgGGMA8GA1UdEwEB/wQF\n" +
                "MAMBAf8wHQYDVR0OBBYEFLE+w2kD+L9HAdSYJhoIAu9jZCvDMB8GA1UdIwQYMBaA\n" +
                "FLE+w2kD+L9HAdSYJhoIAu9jZCvDMA0GCSqGSIb3DQEBBQUAA4IBAQAcGgaX3Nec\n" +
                "nzyIZgYIVyHbIUf4KmeqvxgydkAQV8GK83rZEWWONfqe/EW1ntlMMUu4kehDLI6z\n" +
                "eM7b41N5cdblIZQB2lWHmiRk9opmzN6cN82oNLFpmyPInngiK3BD41VHMWEZ71jF\n" +
                "hS9OMPagMRYjyOfiZRYzy78aG6A9+MpeizGLYAiJLQwGXFK3xPkKmNEVX58Svnw2\n" +
                "Yzi9RKR/5CYrCsSXaQ3pjOLAEFe4yHYSkVXySGnYvCoCWw9E1CAx2/S6cCZdkGCe\n" +
                "vEsXCS+0yx5DaMkHJ8HSXPfqIbloEpw8nL+e/IBcm2PN7EeqJSdnoDfzAIJ9VNep\n" +
                "+OkuE6N36B9K\n" +
                "-----END CERTIFICATE-----";
//        cert = "-----BEGIN CERTIFICATE-----\n" +
//                "MIIDyTCCArGgAwIBAgIEVGIKZjANBgkqhkiG9w0BAQsFADCBijEUMBIGA1UEBhML\n" +
//                "UG9ydFN3aWdnZXIxFDASBgNVBAgTC1BvcnRTd2lnZ2VyMRQwEgYDVQQHEwtQb3J0\n" +
//                "U3dpZ2dlcjEUMBIGA1UEChMLUG9ydFN3aWdnZXIxFzAVBgNVBAsTDlBvcnRTd2ln\n" +
//                "Z2VyIENBMRcwFQYDVQQDEw5Qb3J0U3dpZ2dlciBDQTAeFw0xNDExMTExMzA4NTRa\n" +
//                "Fw0zNzExMTExMzA4NTRaMIGKMRQwEgYDVQQGEwtQb3J0U3dpZ2dlcjEUMBIGA1UE\n" +
//                "CBMLUG9ydFN3aWdnZXIxFDASBgNVBAcTC1BvcnRTd2lnZ2VyMRQwEgYDVQQKEwtQ\n" +
//                "b3J0U3dpZ2dlcjEXMBUGA1UECxMOUG9ydFN3aWdnZXIgQ0ExFzAVBgNVBAMTDlBv\n" +
//                "cnRTd2lnZ2VyIENBMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhJNH\n" +
//                "lMhKIi1mUlyA0zzT6RSvdA6YOiFyXO60Hnth6t4qrP1eeWWpeS22FX0NyuBmU6Ip\n" +
//                "+/e0ntRb5+ng8shbsbnrA+qbBzhDqUNmpEP/kN1/g5o6RdQIJ24Sl//VB2QLv9eZ\n" +
//                "itPNMa8siYf8qDAMqyomXrahOGeLXfI66F4fdbZeJXk4oSTUT11uuBuLA5aHE6TL\n" +
//                "rmm4MZgSpHAz81iZ9Vta8U/kcG+Jxiec0VVJju8JX7ofFaBcLcWQb1Kru+wToBoB\n" +
//                "fXDQ8XecOKJZaM/IX7E+Q9X/UuWLT2kNAxuDk4lUOIiJEXS1c73QkmRJxqGhudBE\n" +
//                "YdUiIIHuCpXn45FsIwIDAQABozUwMzASBgNVHRMBAf8ECDAGAQH/AgEAMB0GA1Ud\n" +
//                "DgQWBBSQafIKyS23KlfIJpkZ9/dPTGMTsTANBgkqhkiG9w0BAQsFAAOCAQEATu3H\n" +
//                "7RXq/gF22bAH6sUZqK1LAdtvQERmnk8tuP6Ak1N4CmgxCo/aR6W4mQ3OA2Y6AnGW\n" +
//                "F+Smwk2CGrl0A9RogTJ54ujD2FWkuNVNAkuUnRtRwQ5G0v170eKv4eLPN2AAFnv9\n" +
//                "mm7hg6OOP1568avjORiTigl6ON/kK2PPJeP6eWSEJrVPh3PyZ60IrHggCXhZgdUi\n" +
//                "4H9vUALcLxgprYeDBThkyLEL9rDksSSJfwJbEG4QgInSL/Kf4GEYj34Rzg532fX0\n" +
//                "45zOqtsgalLCCy5dVGScnvtdJRaTuIsDyKo7eusmXrJCfEHd5TTBVdr+QVKz8qSV\n" +
//                "gyqY8ise+eO62RBoUg==\n" +
//                "-----END CERTIFICATE-----\n";
        keyStorePassword = "qlvbdhcaobangpassword";
        Realm.init(this);
    }

}