package report;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnection;

import models.ArticleBean;

public class ReportReceiver {

	// URL of the JMS server
	// default broker URL is : tcp://localhost:61616"
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	// Name of the queue we will receive messages from
	private static String subject = "SolideQueue";;

	public List<ArticleBean> receivedArticles() throws JMSException, NamingException {
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		InitialContext ctx = new InitialContext(props);
		// get the initial context
		// InitialContext ctx = new InitialContext();
		ConnectionFactory connFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
		// create a queue connection
		Connection queueConn = connFactory.createConnection();
		queueConn.start();
		// lookup the queue object
		Session session = queueConn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(subject);

		MessageConsumer consumer = session.createConsumer(destination);
		// build list of articles from received message
		List<ArticleBean> myArticlesList = new ArrayList<ArticleBean>();
		while (true) {
			Message message = consumer.receive(5000);
			if (message instanceof TextMessage) {

				ArticleBean articleBean = new ArticleBean();
				articleBean.setArticleNo(message.getIntProperty("artNo"));
				articleBean.setArticleName(message.getStringProperty("artName"));
				articleBean.setArticleDescription(message.getStringProperty("artDesc"));
				articleBean.setArticlePrice(message.getIntProperty("artPrice"));
				articleBean.setArticleType(message.getStringProperty("artType"));
				articleBean.setArticleModel(message.getStringProperty("artModel"));
				articleBean.setArticleCategory(message.getIntProperty("artCategory"));
				articleBean.setArticleOldNew(message.getStringProperty("artOldNew"));
				articleBean.setArticleStatus(message.getStringProperty("artStatus"));
				myArticlesList.add(articleBean);
			} else {
				System.out.println("Queue Empty");
				queueConn.stop();
				break;
			}
		}

		queueConn.close();
		return myArticlesList;
	}
}
