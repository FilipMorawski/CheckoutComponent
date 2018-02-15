package com.filipmorawski.checkoutcomponent.interfaces;

import java.math.BigDecimal;

public interface Visitable {

	public BigDecimal accept(Visitor visitor);
	
}
