/**
 * Created with IntelliJ IDEA.
 * User: Woody SY
 * Date: 22/05/2020
 * Time: 5:11 오후
 * Copyright Coupang. All rights reserved.
 */
public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();
        ListNode tmpNode = result;

        int carry = 0;
        while (l1 != null || l2 != null || carry!=0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            tmpNode.next = new ListNode(sum % 10);
            tmpNode = tmpNode.next;

            carry = sum / 10;
        }

        return result.next;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
