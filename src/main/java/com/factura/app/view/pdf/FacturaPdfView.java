package com.factura.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.factura.app.models.entity.Factura;
import com.factura.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localeResolver;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		// String rutaImagen = "./resources/static/images/AleApp.jpg"; // Reemplaza esto con la ruta correcta de tu imagen

         // Crea una instancia de la imagen
        // Image imagen = Image.getInstance(rutaImagen);
         
         
         
        // PdfPCell cellImagen = new PdfPCell();
         
       //  cellImagen.setImage(imagen);

		Factura factura = (Factura) model.get("factura");

		Locale locale = localeResolver.resolveLocale(request);

		MessageSourceAccessor mensajes = getMessageSourceAccessor();

		PdfPTable containerTable = new PdfPTable(1);
		containerTable.setWidthPercentage(100);

//principal
		PdfPTable tabla = new PdfPTable(1);
	//	tabla.addCell(cellImagen);
		tabla.setSpacingAfter(20);

		PdfPCell cell = null;

		cell = new PdfPCell(
				new Phrase(Font.ITALIC, messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
		cell.setBackgroundColor(new Color(0, 255, 51));
		cell.setPadding(8f);
		cell.setBorderColor(new Color(0, 255, 51));
		cell.setBorderColorRight(Color.BLACK);
		cell.setBorderColorLeft(Color.BLACK);
		cell.setBorderColorBottom(Color.BLACK);
		cell.setUseBorderPadding(true);

		PdfPCell cellDatoUsuario = null;
		cellDatoUsuario = new PdfPCell(
				new Phrase("Nombre completo: 	" + factura.getUsuario().getNombre() + " " + factura.getUsuario().getApellido()));
		cellDatoUsuario.setBackgroundColor(new Color(204, 204, 204));
		cellDatoUsuario.setPadding(8f);
		cellDatoUsuario.setBorderColor(new Color(204, 204, 204));

		PdfPCell cellDatoUsuarioApellido = null;
		cellDatoUsuarioApellido = new PdfPCell(new Phrase(Font.BOLDITALIC, "Email:	 " + factura.getUsuario().getEmail()));
		cellDatoUsuarioApellido.setBackgroundColor(new Color(204, 204, 204));
		cellDatoUsuarioApellido.setPadding(8f);
		cellDatoUsuarioApellido.setBorderColor(new Color(204, 204, 204));

		
		
		tabla.addCell(cell);
		tabla.addCell(cellDatoUsuario);
		tabla.addCell(cellDatoUsuarioApellido);
		//tabla.addCell(factura.getUsuario().getEmail());

		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);

		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
		cell.setBackgroundColor(new Color(0, 255, 51));
		cell.setBorderColor(new Color(0, 255, 51));
		cell.setPadding(8f);

		tabla2.addCell(cell);
		
		
		

		PdfPCell folio = null;
		folio = new PdfPCell(new Phrase(Font.BOLDITALIC, "folio:	 " + factura.getId()));
		folio.setBackgroundColor(new Color(204, 204, 204));
		folio.setPadding(8f);
		folio.setBorderColor(new Color(204, 204, 204));
		

		PdfPCell descripcion = null;
		descripcion = new PdfPCell(new Phrase(Font.BOLDITALIC, "descripcion:	 " + factura.getDescripcion()));
		descripcion.setBackgroundColor(new Color(204, 204, 204));
		descripcion.setPadding(8f);
		descripcion.setBorderColor(new Color(204, 204, 204));
		
		PdfPCell fecha = null;
		fecha = new PdfPCell(new Phrase(Font.BOLDITALIC, "fecha:	 " + factura.getCreateAt()));
		fecha.setBackgroundColor(new Color(204, 204, 204));
		fecha.setPadding(8f);
		fecha.setBorderColor(new Color(204, 204, 204));
		
		
		tabla2.addCell(folio);
	
		tabla2.addCell(descripcion);
	
		tabla2.addCell(fecha);
		//tabla2.addCell(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
		//tabla2.addCell(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		//tabla2.addCell(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());

		document.add(tabla);
		document.add(tabla2);

		PdfPTable tabla3 = new PdfPTable(4);
		tabla3.setWidths(new float[] { 3.5f, 2, 2, 2 });
		
		PdfPCell nombre = null;
		nombre = new PdfPCell(new Phrase(Font.BOLDITALIC, "NOMBRE:"));
		nombre.setBackgroundColor(new Color(0, 255, 51));
		//nombre.setPadding(8f);
		//nombre.setBorderColor(new Color(204, 204, 204));
		
		PdfPCell precio = null;
		precio = new PdfPCell(new Phrase(Font.BOLDITALIC, "PRECIO" ));
		precio.setBackgroundColor(new Color(0, 255, 51));
		//precio.setPadding(8f);
	//	precio.setBorderColor(new Color(204, 204, 204));
		
		
		PdfPCell cantidad = null;
		cantidad = new PdfPCell(new Phrase(Font.BOLDITALIC, "CANTIDAD" ));
		cantidad.setBackgroundColor(new Color(0, 255, 51));
		//cantidad.setPadding(8f);
		//cantidad.setBorderColor(new Color(204, 204, 204));
		
		
		PdfPCell total = null;
		total = new PdfPCell(new Phrase(Font.BOLDITALIC, "TOTAL" ));
		total.setBackgroundColor(new Color(0, 255, 51));
		//total.setPadding(8f);
		//total.setBorderColor(new Color(204, 204, 204));
		
		
		tabla3.addCell(nombre);
		tabla3.addCell(precio);
		tabla3.addCell(cantidad);
		tabla3.addCell(total);
		
		
		

		for (ItemFactura item : factura.getItems()) {
			
			PdfPCell getNombreEmpresa = null;
			getNombreEmpresa = new PdfPCell(new Phrase(Font.BOLDITALIC, item.getNombreEmpresa()));
			getNombreEmpresa.setBackgroundColor(new Color(204, 204, 204));
			//getNombreEmpresa.setPadding(8f);
		//	getNombreEmpresa.setBorderColor(new Color(204, 204, 204));
			
			
			
			
			tabla3.addCell(getNombreEmpresa);
			
			PdfPCell getPrecio = null;
			getPrecio = new PdfPCell(new Phrase(Font.BOLDITALIC, item.getPrecio().toString()));
			getPrecio.setBackgroundColor(new Color(204, 204, 204));
			//getPrecio.setPadding(8f);
			//getPrecio.setBorderColor(new Color(204, 204, 204));
			
			tabla3.addCell(getPrecio);
			
			PdfPCell getCantidad = null;
			getCantidad = new PdfPCell(new Phrase(Font.BOLDITALIC, item.getCantidad().toString()));
			getCantidad.setBackgroundColor(new Color(204, 204, 204));
			//getCantidad.setPadding(8f);
		//	getCantidad.setBorderColor(new Color(204, 204, 204));
			
			tabla3.addCell(getCantidad);
			
		//	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			//tabla3.addCell(cell);
			
			
			PdfPCell calcula = null;
			calcula = new PdfPCell(new Phrase(Font.BOLDITALIC, item.calcula().toString()));
			calcula.setBackgroundColor(new Color(204, 204, 204));
			//calcula.setPadding(8f);
		//	calcula.setBorderColor(new Color(204, 204, 204));
			
			tabla3.addCell(calcula);
		}

		PdfPCell celdaTotal = null;
		
		celdaTotal = new PdfPCell(new Phrase("Total: "));
		celdaTotal.setColspan(3);
		celdaTotal.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		celdaTotal.setBackgroundColor(new Color(204, 204, 204));
		tabla3.addCell(celdaTotal);
		
		PdfPCell getTotal = null;
		getTotal = new PdfPCell(new Phrase(Font.BOLDITALIC, factura.getTotal().toString()));
		getTotal.setBackgroundColor(new Color(204, 204, 204));
		
		
		//calcula.setPadding(8f);
	//	calcula.setBorderColor(new Color(204, 204, 204));
		
		tabla3.addCell(getTotal);
		//tabla3.addCell(cellImagen);
		//tabla3.addCell(factura.getTotal().toString());

		document.setMargins(50, 50, 50, 50); // Márgenes
		document.add(tabla3);

	}

}
