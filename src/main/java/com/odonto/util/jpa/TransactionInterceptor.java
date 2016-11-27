package com.odonto.util.jpa;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Interceptor
@Transactional
public class TransactionInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	private @Inject EntityManager manager;

//	private static Logger logger = Logger.getLogger(TransactionInterceptor.class);

	@AroundInvoke
	public Object invoke(InvocationContext context) throws Exception {
		
//		for (Object item: context.getParameters()) {
//			gravaLog(item);
//		}
		
		EntityTransaction trx = manager.getTransaction();
		boolean criador = false;

		try {
			if (!trx.isActive()) {
				// truque para fazer rollback no que já passou
				// (senão, um futuro commit, confirmaria até mesmo operações sem transação)
				trx.begin();
				trx.rollback();
				
				// agora sim inicia a transação
				trx.begin();
				
				criador = true;
			}

			return context.proceed();
		} catch (Exception e) {
			if (trx != null && criador) {
				trx.rollback();
			}

			throw e;
		} finally {
			if (trx != null && trx.isActive() && criador) {
				trx.commit();
			}
		}
	}
	
//	private static <T>void gravaLog(T obj)
//	{
//		if (obj != null) {
//			StringBuilder strLog = new StringBuilder();
//			strLog.append("# " + obj.getClass().getName() + " { ");
//		    for (java.lang.reflect.Field field : obj.getClass().getDeclaredFields()) {
//		        field.setAccessible(true);
//		        String name = field.getName();
//		        if (!name.contains("serialVersionUID")) {
//			        Object value = null;
//			        try{ value = field.get(obj); }
//			        catch(Throwable e){}
//			        strLog.append(" [" + name + " = " + value + "] ");	        	
//		        }
//		    }
//		    strLog.append(" }");
//		    logger.info(strLog.toString());			
//		}
//	}
	
}