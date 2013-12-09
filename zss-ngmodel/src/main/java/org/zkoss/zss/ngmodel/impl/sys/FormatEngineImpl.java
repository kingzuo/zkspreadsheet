package org.zkoss.zss.ngmodel.impl.sys;

import org.zkoss.poi.ss.format.CellFormat;
import org.zkoss.poi.ss.usermodel.ZssContext;
import org.zkoss.zss.ngmodel.NCell;
import org.zkoss.zss.ngmodel.NCell.CellType;
import org.zkoss.zss.ngmodel.sys.EngineFactory;
import org.zkoss.zss.ngmodel.sys.format.FormatContext;
import org.zkoss.zss.ngmodel.sys.format.FormatEngine;
import org.zkoss.zss.ngmodel.sys.format.FormatResult;

public class FormatEngineImpl implements FormatEngine {

	@Override
	public FormatResult format(String format, Object value, FormatContext context){
		ZssContext old = ZssContext.getThreadLocal();
		try{
			ZssContext zssContext = old==null?new ZssContext(context.getLocale(),-1): new ZssContext(context.getLocale(),old.getTwoDigitYearUpperBound());
			ZssContext.setThreadLocal(zssContext);
			CellFormat formatter = CellFormat.getInstance(format, context.getLocale());
			if(value instanceof Double && formatter.isApplicableDateFormat((Double)value)){
				value = EngineFactory.getInstance().getCalendarUtil().doubleValueToDate((Double)value, false);
			}
			return new FormatResultImpl(formatter.apply(value));
		}finally{
			ZssContext.setThreadLocal(old);
		}
	}

}
