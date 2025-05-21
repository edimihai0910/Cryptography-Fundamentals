package certificates;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class CertificateV3 {

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("prime256v1");
        keyPairGenerator.initialize(ecGenParameterSpec);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        System.out.println("Public Key: " + keyPair.getPublic());
        System.out.println("Private Key: " + keyPair.getPrivate());

        //generate V3 certificate (root) -- signed by own private key
        String distingushedName = "CN=com.editza.cryptography, O=Global Organization Edi is A CEO LTD, OU=RO, L=Bucharest, C=Romania";
        X509Certificate certificate = generateCertificate(keyPair.getPrivate(), keyPair.getPublic(), distingushedName);
        System.out.println("Certificate: \n" + certificate);
        System.out.println(transformCertificate(certificate));
    }

    private static String transformCertificate(X509Certificate certificate) throws Exception {
        return "-----BEGIN CERTIFICATE-----\n" +
                Base64.getEncoder().encodeToString(certificate.getEncoded())+
                "\n-----END CERTIFICATE-----\n";
    }

    private static X509Certificate generateCertificate(PrivateKey aPrivate, PublicKey aPublic, String distinguishedName) throws Exception {
       // validity period setup
        Calendar calendar = Calendar.getInstance();
        Date notBefore = calendar.getTime(); // now
        calendar.add(Calendar.YEAR, 1);
        Date notAfter = calendar.getTime(); //now + 1 year

//      DISTINGUISHED NAME
//        CN=Common Name
//        O = Organization
//        C = Country
        X500Name subjectName = new X500Name(distinguishedName);

        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                subjectName,                                    // issuer - who is creating the certificate
                BigInteger.valueOf(System.currentTimeMillis()), // serial number - a unique ID for the certificate
                notBefore,
                notAfter,
                subjectName,                                    // subject - Who the certificate is for - here same as issue because  is self-signed
                aPublic
        );

        try {
            JcaX509ExtensionUtils x509ExtensionUtils = new JcaX509ExtensionUtils();
            certBuilder.addExtension(Extension.basicConstraints,true,new BasicConstraints(true)); // tell others what the ceert can be used for - true means  act as a CA
            certBuilder.addExtension(Extension.subjectKeyIdentifier,false,x509ExtensionUtils.createSubjectKeyIdentifier(aPublic)); // a hash of the public key used to identify the key
            certBuilder.addExtension(Extension.authorityKeyIdentifier,false,x509ExtensionUtils.createAuthorityKeyIdentifier(aPublic)); //identifies the issuer's public key

            JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder("SHA256withECDSA"); // prepares a digital signature algorithm SHA256 with ECDSA - approved for who ever have the private key

            return new JcaX509CertificateConverter().getCertificate(certBuilder.build(signerBuilder.build(aPrivate)));
        } catch (GeneralSecurityException | OperatorCreationException | IOException e)
        {
            throw new Exception(e);
        }
    }
}
