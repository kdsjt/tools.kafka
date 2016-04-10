package com.iecas.kds.tools.kafka.kafkaUtils;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/**
 * ObjectEncoder
 * 
 * @Author ����ͥ
 * @Time 2014-07-18
 * 
 */
public class ObjectEncoder implements Encoder<Object>{

	 public ObjectEncoder(VerifiableProperties props) {
		 
	 }

	@Override
	public byte[] toBytes(Object object) {
		//System.out.println("encoder ---> " + object);
		return BeanUtils.object2Bytes(object);
	}
}
