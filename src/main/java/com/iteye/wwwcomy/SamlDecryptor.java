//import java.io.InputStream;
//import java.security.KeyStore;
//import java.security.interfaces.RSAPrivateKey;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.xml.validation.Schema;
//
//import org.opensaml.DefaultBootstrap;
//import org.opensaml.common.xml.SAMLSchemaBuilder;
//import org.opensaml.saml2.core.Assertion;
//import org.opensaml.saml2.core.Attribute;
//import org.opensaml.saml2.core.AttributeStatement;
//import org.opensaml.saml2.core.EncryptedAssertion;
//import org.opensaml.saml2.core.EncryptedAttribute;
//import org.opensaml.saml2.encryption.Decrypter;
//import org.opensaml.saml2.encryption.EncryptedElementTypeEncryptedKeyResolver;
//import org.opensaml.xml.Configuration;
//import org.opensaml.xml.encryption.ChainingEncryptedKeyResolver;
//import org.opensaml.xml.encryption.InlineEncryptedKeyResolver;
//import org.opensaml.xml.encryption.SimpleRetrievalMethodEncryptedKeyResolver;
//import org.opensaml.xml.io.Unmarshaller;
//import org.opensaml.xml.io.UnmarshallerFactory;
//import org.opensaml.xml.parse.BasicParserPool;
//import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
//import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
//import org.opensaml.xml.security.x509.BasicX509Credential;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
///**
// * Using samlKeystore.jks, this one is get from the official website of spring
// * security saml.
// * 
// * The input string should be the ADFS's encrypted SAML token.
// * 
// * @author liuxingn
// *
// */
//public class SamlDecryptor {
//	public static BasicParserPool parserPoolManager;
//	static {
//		try {
//			DefaultBootstrap.bootstrap();
//			Schema schema = SAMLSchemaBuilder.getSAML11Schema();
//			parserPoolManager = new BasicParserPool();
//			parserPoolManager.setNamespaceAware(true);
//			parserPoolManager.setIgnoreElementContentWhitespace(true);
//			parserPoolManager.setSchema(schema);
//		} catch (Exception anyE) {
//			final String errorMessage = "Error initialising OpenSAML library";
//			throw new RuntimeException(errorMessage, anyE);
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//		new SamlDecryptor().decrypt();
//	}
//
//	private void decrypt() throws Exception {
//		InputStream inputStream = this.getClass().getResourceAsStream("SAML.xml");
//		Document document = parserPoolManager.parse(inputStream);
//		Element metadataRoot = document.getDocumentElement();
//
//		// Unmarshall
//		UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
//		Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(metadataRoot);
//		EncryptedAssertion encryptedAssertion = (EncryptedAssertion) unmarshaller.unmarshall(metadataRoot);
//
//		KeyStore ks = KeyStore.getInstance("JKS");
//		String password = "nalle123";
//		ks.load(this.getClass().getResourceAsStream("samlKeystore.jks"), password.toCharArray());
//		RSAPrivateKey privateKey = (RSAPrivateKey) ks.getKey("apollo", password.toCharArray());
//
//		// Create the credentials.
//		BasicX509Credential decryptionCredential = new BasicX509Credential();
//		decryptionCredential.setPrivateKey(privateKey);
//		KeyInfoCredentialResolver resolver = new StaticKeyInfoCredentialResolver(decryptionCredential);
//
//		ChainingEncryptedKeyResolver encryptedKeyResolver = new ChainingEncryptedKeyResolver();
//		encryptedKeyResolver.getResolverChain().add(new InlineEncryptedKeyResolver());
//		encryptedKeyResolver.getResolverChain().add(new EncryptedElementTypeEncryptedKeyResolver());
//		encryptedKeyResolver.getResolverChain().add(new SimpleRetrievalMethodEncryptedKeyResolver());
//		// Create a decrypter.
//		Decrypter decrypter = new Decrypter(null, resolver, encryptedKeyResolver);
//
//		// Decrypt the assertion.
//		Assertion decryptedAssertion = null;
//
//		try {
//			decryptedAssertion = decrypter.decrypt(encryptedAssertion);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (decryptedAssertion != null) {
//			System.out.println(decryptedAssertion.getID());
//			System.out.println(decryptedAssertion.getSubject().getNameID().getValue());
//			System.out.println(decryptedAssertion.getIssuer().getValue());
//		}
//		System.out.println("*****");
//		List<Attribute> attributes = new ArrayList<Attribute>();
//		for (AttributeStatement attStatement : decryptedAssertion.getAttributeStatements()) {
//			for (Attribute att : attStatement.getAttributes()) {
//				attributes.add(att);
//			}
//			for (EncryptedAttribute att : attStatement.getEncryptedAttributes()) {
//				Attribute decryptedAttribute = decrypter.decrypt(att);
//				attributes.add(decryptedAttribute);
//			}
//		}
//		for (Attribute a : attributes) {
//			System.out.println(a.getName());
//		}
//	}
//}
