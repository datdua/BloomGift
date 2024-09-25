import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getAllProducts, getProductByStatusTrue, getProductByStatusFalse } from '../../../redux/actions/productActions';
import { Button, Input, Select, Table, Empty, Tabs } from 'antd';
import { PlusOutlined, SearchOutlined } from '@ant-design/icons';
import './ProductList.css'; // Import file CSS tùy chỉnh

const { Option } = Select;
const { TabPane } = Tabs;

const ProductList = () => {
    const dispatch = useDispatch();
    const products = useSelector(state => state.productData.products) || [];
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedCategory, setSelectedCategory] = useState('');

    useEffect(() => {
        dispatch(getAllProducts());
    }, [dispatch]);

    const handleTabChange = (key) => {
        switch (key) {
            case 'all':
                dispatch(getAllProducts());
                break;
            case 'active':
                dispatch(getProductByStatusTrue());
                break;
            case 'violated':
                dispatch(getProductByStatusFalse());
                break;
            default:
                break;
        }
    };

    const columns = [
        {
            title: 'Tên sản phẩm',
            dataIndex: 'productName',
            key: 'productName',
        },
        {
            title: 'Doanh số',
            dataIndex: 'sold',
            key: 'sold',
            sorter: (a, b) => a.sold - b.sold,
        },
        {
            title: 'Giá',
            dataIndex: 'price',
            key: 'price',
            render: (price) => `$${price.toFixed(2)}`,
            sorter: (a, b) => a.price - b.price,
        },
        {
            title: 'Kho hàng',
            dataIndex: 'quantity',
            key: 'quantity',
            sorter: (a, b) => a.quantity - b.quantity,
        },
        {
            title: 'Chất Lượng Nội Dung',
            key: 'description',
            dataIndex: 'description',
            render: description => (
                <span>
                    {description.length > 50 ? description.slice(0, 50) + '...' : description}
                </span>
            ),
        },
        {
            title: 'Thao tác',
            key: 'action',
            render: () => (
                <Button type="link">Chỉnh sửa</Button>
            ),
        },
    ];

    const filteredProducts = products.filter(product =>
        product.productName?.toLowerCase().includes(searchTerm.toLowerCase()) &&
        (selectedCategory === '' || product.categoryName === selectedCategory)
    );

    const categories = [...new Set(products.map(product => product.categoryName))];

    return (
        <div style={{ padding: '20px' }}>
            <h1>Sản phẩm</h1>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '20px' }}>
                <Select defaultValue="" style={{ width: 200, borderColor: '#F56285' }} onChange={value => setSelectedCategory(value)}>
                    <Option value="">Tất cả danh mục</Option>
                    {categories.map(category => (
                        <Option key={category} value={category}>{category}</Option>
                    ))}
                </Select>
                <Button type="primary" style={{ background: '#F56285', borderColor: '#F56285' }} icon={<PlusOutlined />}>
                    Thêm 1 sản phẩm mới
                </Button>
            </div>
            <Input
                placeholder="Tìm tên sản phẩm, SKU sản phẩm, SKU phân loại, Mã sản phẩm"
                onChange={e => setSearchTerm(e.target.value)}
                style={{ marginBottom: '20px', borderColor: '#F56285' }}
                suffix={<SearchOutlined style={{ fontSize: '18px', color: '#bfbfbf' }} />}
            />
            <Tabs defaultActiveKey="all" onChange={handleTabChange}>
                <TabPane tab="Tất cả" key="all">
                    <Table
                        columns={columns}
                        dataSource={filteredProducts}
                        rowKey="productID"
                        locale={{
                            emptyText: (
                                <Empty
                                    image={Empty.PRESENTED_IMAGE_DEFAULT}
                                    description="Không tìm thấy sản phẩm"
                                />
                            )
                        }}
                    />
                </TabPane>
                <TabPane tab="Đang Hoạt Động" key="active">
                    <Table
                        columns={columns}
                        dataSource={filteredProducts}
                        rowKey="productID"
                        locale={{
                            emptyText: (
                                <Empty
                                    image={Empty.PRESENTED_IMAGE_DEFAULT}
                                    description="Không tìm thấy sản phẩm"
                                />
                            )
                        }}
                    />
                </TabPane>
                <TabPane tab="Vi Phạm" key="violated">
                    <Table
                        columns={columns}
                        dataSource={filteredProducts}
                        rowKey="productID"
                        locale={{
                            emptyText: (
                                <Empty
                                    image={Empty.PRESENTED_IMAGE_DEFAULT}
                                    description="Không tìm thấy sản phẩm"
                                />
                            )
                        }}
                    />
                </TabPane>
            </Tabs>
        </div>
    );
};

export default ProductList;