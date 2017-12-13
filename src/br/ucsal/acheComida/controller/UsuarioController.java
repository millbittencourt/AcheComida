package br.ucsal.acheComida.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ucsal.acheComida.dao.ProdutoDAO;
import br.ucsal.acheComida.dao.UsuarioDAO;
import br.ucsal.acheComida.model.Produto;
import br.ucsal.acheComida.model.Usuario;

@WebServlet("/usuarios")
public class UsuarioController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String q = request.getParameter("q");
		
		if (q != null && q.equals("new")) {
			request.getRequestDispatcher("usuarioForm.jsp").forward(request, response);
			return;
		}
		

		UsuarioDAO dao = new UsuarioDAO();

		if (q != null && q.equals("editar")) {
			String id = request.getParameter("id");
			Usuario usuario = dao.getByID(Integer.parseInt(id));
			request.setAttribute("usuario", usuario);
			request.getRequestDispatcher("usuarioForm.jsp").forward(request, response);
		}

		
		if (q != null && q.equals("excluir")) {
				String id = request.getParameter("id");
				dao.delete(Integer.parseInt(id));
		}
		
		request.setAttribute("lista", dao.listar());
		request.getRequestDispatcher("usuarioList.jsp").forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String telefone = request.getParameter("telefone");
		String produtoID = request.getParameter("produto");
		
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		usuario.setTelefone(telefone);
		
		ProdutoDAO produtoDAO = new ProdutoDAO();
		int idProduto = Integer.parseInt(produtoID);
		Produto produto = produtoDAO.getByID(idProduto);
		
		usuario.setProduto(produto);
		
		UsuarioDAO dao = new UsuarioDAO();
		dao.inserir(usuario);
		
		if(id != null && !id.isEmpty()) {
			usuario.setId(Integer.parseInt(id));
			dao.update(usuario);
		}else {
			dao.inserir(usuario);
		}
		

		request.setAttribute("lista", dao.listar());
		request.getRequestDispatcher("usuarioList.jsp").forward(request, response);

	}
}