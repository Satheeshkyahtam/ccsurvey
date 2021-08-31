package com.godrej.surveys;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.ChannelSftp.LsEntry;

@Configuration
public class SftpConfig {

    @Value("${sftp.host}")
    private String sftpHost;

    @Value("${sftp.port:22}")
    private int sftpPort;

    @Value("${sftp.user}")
    private String sftpUser;

//    @Value("${sftp.privateKey:#{null}}")
    @Value("${sftp.privateKey}")
    private Resource sftpPrivateKey;

    @Value("${sftp.privateKeyPassphrase}")
    private String sftpPrivateKeyPassphrase;

//    @Value("${sftp.password:#{null}}")
    @Value("${sftp.password}")
    private String sftpPasword;

    @Value("${sftp.remote.directory:/home/godrej_properties/litmus/feedbacks/customer_onboarding_survey/incoming}")
   // @Value("${sftp.remote.directory}")
    private String sftpRemoteDirectory;
    

    public void setSftpRemoteDirectory(String sftpRemoteDirectory) {
		this.sftpRemoteDirectory = sftpRemoteDirectory;
	}

	@Bean
    public SessionFactory<LsEntry> sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(sftpHost);
        factory.setPort(sftpPort);
        factory.setUser(sftpUser);
        if (sftpPrivateKey != null) {
            factory.setPrivateKey(sftpPrivateKey);
            factory.setPrivateKeyPassphrase(sftpPrivateKeyPassphrase);
        } else {
            factory.setPassword(sftpPasword);
        }
        factory.setAllowUnknownKeys(true);
        return new CachingSessionFactory<LsEntry>(factory);
    }

    @Bean
    @ServiceActivator(inputChannel = "toSftpChannel")
    public MessageHandler handler() {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
        //handler.setRemoteDirectoryExpression(new LiteralExpression(sftpRemoteDirectory));
        ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
        System.out.println("Headr Path::::::::"+EXPRESSION_PARSER.parseExpression("headers['folderpath']"));
        handler.setRemoteDirectoryExpression(EXPRESSION_PARSER.parseExpression("headers['folderpath']"));
        
        handler.setFileNameGenerator(new FileNameGenerator() {

            @Override
            public String generateFileName(Message<?> message) {
                if (message.getPayload() instanceof File) {
                    return ((File) message.getPayload()).getName();
                } else {
                    throw new IllegalArgumentException("File expected as payload.");
                }
            }

        });
        return handler;
    }

    @MessagingGateway
    public interface UploadGateway {

        @Gateway(requestChannel = "toSftpChannel")
        void upload(File file, @Header("folderpath")String folderpath);

    }

}
