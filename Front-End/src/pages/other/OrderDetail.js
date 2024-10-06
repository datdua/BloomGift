import React from 'react';
import { Card, Row, Col, Timeline, Button, Table } from 'antd';
import { CheckCircleOutlined, DollarCircleOutlined, CarOutlined, InboxOutlined, StarOutlined } from '@ant-design/icons';

const OrderDetail = ({ order }) => {
  const orderStatus = [
    { status: 'Đơn Hàng Đã Đặt', icon: <InboxOutlined />, time: order.startDate },
    { status: 'Đã Xác Nhận Thông Tin Thanh Toán', icon: <DollarCircleOutlined />, time: order.startDate },
    { status: 'Vận Chuyển', icon: <CarOutlined />, time: order.deliveryDateTime },
    { status: 'Chờ Giao Hàng', icon: <InboxOutlined /> },
    { status: 'Đánh Giá', icon: <StarOutlined /> },
  ];

  const columns = [
    { title: 'Sản phẩm', dataIndex: 'productName', key: 'productName' },
    { title: 'Cửa hàng', dataIndex: 'storeName', key: 'storeName' },
    { title: 'Số lượng', dataIndex: 'quantity', key: 'quantity' },
    { title: 'Đơn giá', dataIndex: 'productTotalPrice', key: 'productTotalPrice' },
  ];

  return (
    <div className="container mt-4">
      <Card title={`MÃ ĐƠN HÀNG: ${order.orderID}`} extra={<span className="text-danger">NGƯỜI BÁN ĐÃ XÁC NHẬN ĐƠN HÀNG</span>}>
        <Row gutter={16}>
          <Col span={16}>
            <Timeline mode="alternate">
              {orderStatus.map((item, index) => (
                <Timeline.Item key={index} dot={item.icon} color={index < 3 ? 'green' : 'gray'}>
                  {item.status}
                  <p>{item.time}</p>
                </Timeline.Item>
              ))}
            </Timeline>
          </Col>
          <Col span={8}>
            <p>Đơn hàng sẽ được giao cho đơn vị vận chuyển trước {order.deliveryDateTime}. Bạn có thể kiểm tra hàng sau khi thanh toán.</p>
            <p>Giao nhanh đúng hẹn: nhận Voucher ₫15.000 nếu đơn hàng được giao đến bạn sau ngày {order.deliveryDateTime}.</p>
          </Col>
        </Row>
      </Card>

      <Card title="Thông tin đơn hàng" className="mt-4">
        <Row gutter={16}>
          <Col span={12}>
            <p><strong>Địa chỉ nhận hàng:</strong> {order.deliveryAddress}</p>
            <p><strong>Số điện thoại:</strong> {order.phone}</p>
          </Col>
          <Col span={12}>
            <p><strong>Phương thức thanh toán:</strong> COD</p>
            <p><strong>Tổng tiền:</strong> ₫{order.oderPrice}</p>
          </Col>
        </Row>
      </Card>

      <Card title="Chi tiết đơn hàng" className="mt-4">
        <Table columns={columns} dataSource={order.orderDetails} pagination={false} />
        <Row justify="end" className="mt-4">
          <Col>
            <p><strong>Tổng tiền hàng:</strong> ₫{order.oderPrice}</p>
            <p><strong>Phí vận chuyển:</strong> ₫22.200</p>
            <p><strong>Giảm giá phí vận chuyển:</strong> -₫19.700</p>
            <p><strong>Voucher từ Shopee:</strong> -₫1.500</p>
            <p><strong>Thành tiền:</strong> ₫58.900</p>
          </Col>
        </Row>
      </Card>

      <Row justify="end" className="mt-4">
        <Col>
          <Button type="primary" className="mr-2">Liên Hệ Người Bán</Button>
          <Button>Hủy Đơn Hàng</Button>
        </Col>
      </Row>
    </div>
  );
};

export default OrderDetail;