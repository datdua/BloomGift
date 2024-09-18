import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchSellerInfo } from '../../../redux/actions/storeActions';

const StoreProfile = () => {
    const dispatch = useDispatch();
    const sellerInfo = useSelector(state => state.seller.sellerInfo);
    const [activeTab, setActiveTab] = useState('Thông tin cơ bản');

    useEffect(() => {
        const storeID = localStorage.getItem('storeID');
        if (storeID) {
            dispatch(fetchSellerInfo(storeID));
        } else {
            console.error('No storeID found in localStorage');
        }
    }, [dispatch]);

    const renderTabContent = () => {
        switch (activeTab) {
            case 'Thông tin cơ bản':
                return (
                    <div className="basic-info">
                        <h2>Thông tin cơ bản</h2>
                        <div className="info-row">
                            <span>Tên Shop:</span>
                            <span>{sellerInfo?.storeName || '0967630810phuc'}</span>
                        </div>
                        <div className="info-row">
                            <span>Logo của Shop:</span>
                            <div className="logo-container">
                                {sellerInfo?.logo ? (
                                    <img src={sellerInfo.logo} alt="Shop Logo" />
                                ) : (
                                    <div className="default-logo">P</div>
                                )}
                            </div>
                        </div>
                        <div className="logo-requirements">
                            <ul>
                                <li>Kích thước hình ảnh tiêu chuẩn: Chiều rộng 300px, Chiều cao 300px</li>
                                <li>Dung lượng file tối đa: 2.0MB</li>
                                <li>Định dạng file được hỗ trợ: JPG, JPEG, PNG</li>
                            </ul>
                        </div>
                        <div className="info-row">
                            <span>Mô tả Shop:</span>
                            <span>{sellerInfo?.description || ''}</span>
                        </div>
                    </div>
                );
            case 'Thông tin Thuế':
                return <div>Nội dung Thông tin Thuế</div>;
            case 'Thông tin Định Danh':
                return <div>Nội dung Thông tin Định Danh</div>;
            default:
                return null;
        }
    };

    return (
        <div className="store-profile">
            <div className="tab-header">
                {['Thông tin cơ bản', 'Thông tin Thuế', 'Thông tin Định Danh'].map(tab => (
                    <button
                        key={tab}
                        className={activeTab === tab ? 'active' : ''}
                        onClick={() => setActiveTab(tab)}
                    >
                        {tab}
                    </button>
                ))}
            </div>
            <div className="tab-content">
                {renderTabContent()}
            </div>
            <div className="action-buttons">
                <button className="view-shop">Xem Shop của tôi</button>
                <button className="edit">Chỉnh sửa</button>
            </div>
        </div>
    );
};

export default StoreProfile;