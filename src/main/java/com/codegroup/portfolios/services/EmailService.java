package com.codegroup.portfolios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String mailFrom;

    public void enviarCredenciais(String destinatario, String nome, String email, String senhaGerada) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(destinatario);
        message.setSubject("Bem-vindo(a) ao Codegroup Portfolios");
        message.setText(
                "Olá, " + nome + "!\n\n" +
                "Sua conta foi criada com sucesso. Utilize as credenciais abaixo para acessar o sistema:\n\n" +
                "  E-mail: " + email + "\n" +
                "  Senha:  " + senhaGerada + "\n\n" +
                "Por segurança, recomendamos alterar sua senha após o primeiro acesso.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Codegroup"
        );
        //mailSender.send(message);
    }

}
