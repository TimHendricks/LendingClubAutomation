package example;


import lcapi.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.BindingProvider;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/13/13
 * Time: 12:11 AM
 * To change this template use File | Settings | File Templates.
 */
@WebService()
public class HelloWorld {
  @WebMethod
  public static void main(String[] args) {
      // You cannot retreive a list of loans already invested in using the API.
      // A CSV file of loans invested in can be obtained from this URL
      // https://www.lendingclub.com/account/notesRawData.action
      // the relevant data is LoanId, which is the first column of the CSV
      // before any loan is automatically invested in, its ID should be 
      // verified as not in this list
      // to make extra certain that no loan is invested in twice a separate local record of loanIDs 
      // 
      Long regularAccountID = ;
      lcapi.LendingClubV14 lc = new LendingClubV14_Service().getLendingClubV14Port();
      BindingProvider bp = (BindingProvider) lc;

     Map<String, Object> requestContext =
              bp.getRequestContext();
      requestContext.put(
              BindingProvider.USERNAME_PROPERTY, "tim.l.hendricks@gmail.com");
      requestContext.put(BindingProvider.PASSWORD_PROPERTY, "<PASSWORD>");

      lcapi.LoanBrowseLoans getLoans = new lcapi.LoanBrowseLoans();
      getLoans.setShowAll(true);
      GetPortfoliosResult portfolioResult = new GetPortfoliosResult();

      try {
          portfolioResult = lc.orderGetPortfolios(regularAccountID);
      } catch (BadArgumentException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      } catch (ExcessiveRequestsException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      } catch (InvalidCredentialsException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      } catch (MissingCredentialsException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      } catch (UnauthorizedWebserviceUserException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
     for(PortfolioResult result : portfolioResult.getPortfolioResults()){
         System.out.println(result.getPortfolioName());
         ((BindingProvider) lc).getRequestContext().
     }
      BrowseLoansResult loans = new BrowseLoansResult();
      try {
          loans = lc.loanBrowseLoans(false);
      } catch (ExcessiveRequestsException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      } catch (InvalidCredentialsException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      } catch (MissingCredentialsException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      } catch (UnauthorizedWebserviceUserException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      List<LoanListing> listings = loans.getLoans();
      List<LoanListing> filteredLoans = new ArrayList<LoanListing>();
      List<Order> orders = new ArrayList<Order>();

      Date date = new Date();

      for(LoanListing ll : listings){
        if(ll.getGrade() == lcapi.LoanGrade.E || ll.getGrade() == lcapi.LoanGrade.F || ll.getGrade() == lcapi.LoanGrade.G ){
              if(ll.getEmpLength() >= 5){
                  if(ll.getPurpose() == LoanPurpose.CREDIT_CARD||ll.getPurpose() == LoanPurpose.DEBT_CONSOLIDATION || ll.getPurpose() == LoanPurpose.MEDICAL||ll.getPurpose()==LoanPurpose.HOUSE){
                      if(ll.getCreditInfo().getDelinq2Yrs() == 0){
                          if(ll.getAnnualInc() >= 36000){
                              if(ll.getCreditInfo().getEarliestCrLine().getYear() >= (date.getYear()-10) ){
                                  if(ll.getCreditInfo().getPubRec() == 0){
                                      if(ll.getCreditInfo().getPubRecBankruptcies()  == 0) {
                                          if(ll.getCreditInfo().getMthsSinceLastDelinq() >= 60){
                                            Order order = new Order();
                                            order.setLoanId(ll.getId());
                                            order.setRequestedAmount(25);
                                            if(order.getRequestedAmount() == 25){
                                                orders.add(order);
                                            }
                                          }

                                      }
                                  }

                              }

                              }
                          }
                      }
                  }
              }
          }//for()
      OrderInstruct orderInstruct = new OrderInstruct();
      orderInstruct.getOrders().add((Order) orders);
      
      }//main()
  }//class
